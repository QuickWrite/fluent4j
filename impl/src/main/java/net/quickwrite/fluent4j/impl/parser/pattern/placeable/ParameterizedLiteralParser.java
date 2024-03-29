package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.impl.ast.pattern.container.FluentArgumentContainer;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public abstract class ParameterizedLiteralParser<I, B extends ResultBuilder> implements PlaceableExpressionParser {
    @Override
    public Optional<FluentPlaceable> parse(final ContentIterator iterator, final PlaceableParser placeableParser) {
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

        final ArgumentList attributes = getAttributes(iterator, placeableParser);

        return Optional.of(getInstance(identifier.get(), attributes));
    }

    private ArgumentList getAttributes(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final ArgumentList.PlenaryBuilder attributesContainer = FluentArgumentContainer.plenaryBuilder();

        boolean isFirst = true;
        boolean isNamed = false;
        while (iterator.character() != ')') {
            if (!isFirst) {
                if (iterator.character() != ',') {
                    throw new FluentExpectedException(',', iterator);
                }
                iterator.nextChar();

                ParserUtil.skipWhitespaceAndNL(iterator);
            }
            isFirst = false;

            final int[] position = iterator.position();

            final Optional<Map.Entry<String, ArgumentList.NamedArgument>> namedEntry = tryParseNamed(iterator, placeableParser);

            if (namedEntry.isPresent()) {
                final Map.Entry<String, ArgumentList.NamedArgument> entry = namedEntry.get();

                attributesContainer.add(entry.getKey(), entry.getValue());

                isNamed = true;
            }

            if (isNamed) {
                ParserUtil.skipWhitespaceAndNL(iterator);
                continue;
            }

            iterator.setPosition(position);

            final Optional<FluentPlaceable> placeable = placeableParser.parsePlaceable(iterator);
            if (placeable.isEmpty()) {
                throw new FluentBuilderException("Expected an inline expression", iterator);
            }
            attributesContainer.add(placeable.get());

            ParserUtil.skipWhitespaceAndNL(iterator);
        }

        iterator.nextChar();

        return attributesContainer.build();
    }

    @SuppressWarnings("unchecked")
    private Optional<Map.Entry<String, ArgumentList.NamedArgument>> tryParseNamed(final ContentIterator iterator, final PlaceableParser placeableParser) {
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
            throw new FluentBuilderException("Expected attribute", iterator);
        }

        if (!(placeable.get() instanceof ArgumentList.NamedArgument)) {
            throw new FluentBuilderException("Expected literal", iterator);
        }

        return Optional.of(new AbstractMap.SimpleImmutableEntry<>(identifier.get(), (ArgumentList.NamedArgument) placeable.get()));
    }

    protected abstract Optional<I> parseIdentifier(final ContentIterator iterator);

    protected abstract FluentPlaceable getInstance(final I identifier);

    protected abstract FluentPlaceable getInstance(final I identifier, final ArgumentList argumentList);

    protected abstract boolean optionalArguments();
}
