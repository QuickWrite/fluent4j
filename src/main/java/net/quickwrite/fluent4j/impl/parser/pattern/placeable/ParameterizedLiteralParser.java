package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentAttributeContainer;
import net.quickwrite.fluent4j.impl.ast.pattern.ParameterizedLiteral;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public abstract class ParameterizedLiteralParser<T extends ParameterizedLiteral<?>, I> implements PlaceableExpressionParser<T> {
    @Override
    public Optional<T> parse(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final Optional<I> identifier = parseIdentifier(iterator);
        if (identifier.isEmpty()) {
            return Optional.empty();
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        if (iterator.character() != '(') {
            if (!optionalArguments()) {
                return Optional.empty();
            }

            return Optional.of(getInstance(identifier.get()));
        }

        iterator.nextChar();
        ParserUtil.skipWhitespace(iterator);

        final AttributeList attributes = getAttributes(iterator, placeableParser);

        return Optional.of(getInstance(identifier.get(), attributes));
    }

    private AttributeList getAttributes(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final FluentAttributeContainer attributesContainer = new FluentAttributeContainer();

        boolean isFirst = true;
        boolean isNamed = false;
        while (iterator.character() != ')') {
            if (!isFirst) {
                if (iterator.character() != ',') {
                    throw new RuntimeException("Expected ',' but got '" + Character.toString(iterator.character()) + "'");
                }
                iterator.nextChar();

                ParserUtil.skipWhitespaceAndNL(iterator);
            }
            isFirst = false;

            final int[] position = iterator.position();

            final Optional<Map.Entry<String, AttributeList.NamedAttribute>> namedEntry = tryParseNamed(iterator, placeableParser);

            if (namedEntry.isPresent()) {
                final Map.Entry<String, AttributeList.NamedAttribute> entry = namedEntry.get();

                attributesContainer.addAttribute(entry.getKey(), entry.getValue());

                isNamed = true;
            }

            if (isNamed) {
                ParserUtil.skipWhitespaceAndNL(iterator);
                continue;
            }

            iterator.setPosition(position);

            final Optional<FluentPlaceable> placeable = placeableParser.parsePlaceable(iterator);
            if (placeable.isEmpty()) {
                throw new RuntimeException("Expected an inline expression");
            }
            attributesContainer.addAttribute(placeable.get());

            ParserUtil.skipWhitespaceAndNL(iterator);
        }

        iterator.nextChar();

        return attributesContainer;
    }

    private Optional<Map.Entry<String, AttributeList.NamedAttribute>> tryParseNamed(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);

        if (identifier.isEmpty()) {
            return Optional.empty();
        }

        ParserUtil.skipWhitespace(iterator);

        if (iterator.character() != ':') {
            return Optional.empty();
        }

        iterator.nextChar();
        ParserUtil.skipWhitespaceAndNL(iterator);

        final Optional<FluentPlaceable> placeable = placeableParser.parsePlaceable(iterator);
        if (placeable.isEmpty()) {
            throw new RuntimeException("Expected attribute");
        }

        if (!(placeable.get() instanceof AttributeList.NamedAttribute)) {
            throw new RuntimeException("Expected literal");
        }

        return Optional.of(new AbstractMap.SimpleImmutableEntry<>(identifier.get(), (AttributeList.NamedAttribute) placeable.get()));
    }

    protected abstract Optional<I> parseIdentifier(final ContentIterator iterator);

    protected abstract T getInstance(final I identifier);

    protected abstract T getInstance(final I identifier, final AttributeList attributes);

    protected abstract boolean optionalArguments();
}
