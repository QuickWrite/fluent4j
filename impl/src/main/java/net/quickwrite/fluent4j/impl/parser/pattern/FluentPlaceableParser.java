package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentSelectExpression;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.*;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluentPlaceableParser implements PlaceableParser {
    private final List<PlaceableExpressionParser> parserList;

    private FluentPlaceableParser(final List<PlaceableExpressionParser> parserList) {
        this.parserList = parserList;
    }

    @Override
    public int getStartingChar() {
        return '{';
    }

    @Override
    public ParseResult<FluentPattern> parse(final ContentIterator iterator, final FluentContentParser contentParser) {
        iterator.nextChar();

        ParserUtil.skipWhitespaceAndNL(iterator);

        final FluentPlaceable placeable = parsePlaceable(iterator)
                .orElseThrow(() -> new FluentBuilderException("All PlaceableExpressionParsers returned FAILURE", iterator));

        select:
        if (placeable instanceof FluentSelect.Selectable) {
            ParserUtil.skipWhitespace(iterator);

            final Optional<FluentSelect> selectExpression = parseSelector(
                    iterator,
                    contentParser,
                    (FluentSelect.Selectable) placeable
            );

            if (selectExpression.isEmpty()) {
                break select;
            }

            return ParseResult.success(selectExpression.get());
        }

        if (placeable instanceof FluentPlaceable.CannotPlaceable) {
            throw new FluentBuilderException(
                    "A " + ((FluentPlaceable.CannotPlaceable) placeable).getName() + " cannot be used as a placeable.",
                    iterator
            );
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        if (iterator.character() != '}') {
            throw new FluentExpectedException('}', iterator);
        }

        iterator.nextChar();

        return ParseResult.success(placeable);
    }

    @Override
    public Optional<FluentPlaceable> parsePlaceable(final ContentIterator iterator) {
        final int[] position = iterator.position();

        for (final PlaceableExpressionParser expressionParser : parserList) {
            final Optional<FluentPlaceable> parseResult = expressionParser.parse(iterator, this);

            if (parseResult.isEmpty()) {
                iterator.setPosition(position);
                continue;
            }

            return parseResult;
        }

        return Optional.empty();
    }

    private Optional<FluentSelect> parseSelector(
            final ContentIterator iterator,
            final FluentContentParser contentParser,
            FluentSelect.Selectable selectable
    ) {
        if (iterator.character() != '-') {
            return Optional.empty();
        }

        if (iterator.nextChar() != '>') {
            throw new FluentExpectedException("->", "-" + iterator.character(), iterator);
        }

        iterator.nextChar();

        ParserUtil.skipWhitespace(iterator);

        if (iterator.character() != '\n') {
            throw new FluentExpectedException('\n', iterator);
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        final List<FluentSelect.FluentVariant> variantList = new ArrayList<>();
        FluentSelect.FluentVariant defaultVariant = null;

        while (true) {
            boolean isDefault = false;

            if (iterator.character() == '*') {
                if (defaultVariant != null) {
                    throw new FluentBuilderException("Only one default variant can exist.", iterator);
                }

                isDefault = true;

                iterator.nextChar();
            }

            final Optional<FluentSelect.FluentVariant> variant = parseVariant(iterator, contentParser);

            if (variant.isEmpty()) {
                if (variantList.size() == 0) {
                    throw new FluentBuilderException("Expected at least one variant after \"->\"", iterator);
                }

                // If the default variant is the last variant it can easily be removed
                // as it will always be checked last and so does not need to be in the list
                // of the normal variants.
                if (defaultVariant == variantList.get(variantList.size() - 1)) {
                    variantList.remove(variantList.size() - 1);
                }

                break;
            }

            variantList.add(variant.get());

            if (isDefault) {
                defaultVariant = variant.get();
            }
        }

        if (defaultVariant == null) {
            // TODO: Better error position
            throw new FluentBuilderException("Expected at least one variant to be marked as default (*)", iterator);
        }

        iterator.nextChar();

        if (selectable instanceof IntermediateTextElement) {
            selectable = new FluentTextElement(((IntermediateTextElement) selectable).getContent().toString());
        }

        return Optional.of(new FluentSelectExpression(selectable, variantList, defaultVariant));
    }

    private Optional<FluentSelect.FluentVariant> parseVariant(final ContentIterator iterator, final FluentContentParser contentParser) {
        if (iterator.character() != '[') {
            return Optional.empty();
        }

        iterator.nextChar();

        ParserUtil.skipWhitespace(iterator);

        final FluentSelect.FluentVariant.FluentVariantKey variantKey = getVariantKey(iterator);

        ParserUtil.skipWhitespace(iterator);

        if (iterator.character() != ']') {
            throw new FluentExpectedException(']', iterator);
        }

        iterator.nextChar();

        final List<FluentPattern> content = contentParser.parse(iterator, contentIterator -> {
            ParserUtil.skipWhitespace(contentIterator);

            final int character = contentIterator.character();
            return character == '[' || character == '*' || character == '}';
        });

        return Optional.of(new FluentSelectExpression.FluentVariant(variantKey, content));
    }

    private FluentSelect.FluentVariant.FluentVariantKey getVariantKey(final ContentIterator iterator) {
        final Optional<String> number = FluentNumberLiteralParser.parseNumberLiteral(iterator);
        if (number.isPresent()) {
            return new FluentNumberLiteral(number.get());
        }
        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);

        if (identifier.isEmpty()) {
            throw new FluentBuilderException("Expected identifier", iterator);
        }

        return new FluentTextElement(identifier.get());
    }

    public static PlaceableParser.Builder builder() {
        return new FluentPlaceableParserBuilder();
    }

    private static final class FluentPlaceableParserBuilder implements PlaceableParser.Builder {
        private final List<PlaceableExpressionParser> parserList;

        public FluentPlaceableParserBuilder() {
            this.parserList = new ArrayList<>();
        }

        @Override
        public Builder addParser(final PlaceableExpressionParser parser) {
            this.parserList.add(parser);

            return this;
        }

        @Override
        public PlaceableParser build() {
            return new FluentPlaceableParser(this.parserList);
        }
    }
}
