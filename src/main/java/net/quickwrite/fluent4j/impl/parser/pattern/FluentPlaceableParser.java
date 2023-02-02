package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentSelectExpression;
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
    private final List<PlaceableExpressionParser<? extends FluentPattern>> expressionParserList = new ArrayList<>();

    public static FluentPlaceableParser getBasicParser() {
        final FluentPlaceableParser base = new FluentPlaceableParser();

        base.addParser(new FluentStringLiteralParser());
        base.addParser(new FluentNumberLiteralParser());
        base.addParser(new FluentFunctionParser());

        base.addParser(new FluentTermReferenceParser());
        base.addParser(new FluentMessageReferenceParser());

        base.addParser(new FluentVariableReferenceParser());

        return base;
    }

    public void addParser(final PlaceableExpressionParser<? extends FluentPattern> parser) {
        this.expressionParserList.add(parser);
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
                .orElseThrow(() -> new RuntimeException("All PlaceableExpressionParsers returned FAILURE"));

        select:
        if (placeable instanceof FluentSelect.Selectable) {
            ParserUtil.skipWhitespace(iterator);

            final Optional<FluentSelect> selectExpression = parseSelector(iterator, contentParser, placeable);

            if (selectExpression.isEmpty()) {
                break select;
            }

            return ParseResult.success(selectExpression.get());
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        if (iterator.character() != '}') {
            throw new RuntimeException("Expected '}' but got '" + Character.toString(iterator.character()) + "'");
        }

        if (placeable instanceof FluentPlaceable.CannotPlaceable) {
            throw new RuntimeException(
                    "A " +
                    ((FluentPlaceable.CannotPlaceable) placeable).getName() +
                    " cannot be used as a placeable."
            );
        }

        iterator.nextChar();

        return ParseResult.success(placeable);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<FluentPlaceable> parsePlaceable(final ContentIterator iterator) {
        final int[] position = iterator.position();

        for (final PlaceableExpressionParser<? extends FluentPlaceable> expressionParser : expressionParserList) {
            final Optional<? extends FluentPlaceable> parseResult = expressionParser.parse(iterator, this);

            if (parseResult.isEmpty()) {
                iterator.setPosition(position);
                continue;
            }

            return (Optional<FluentPlaceable>) parseResult;
        }

        return Optional.empty();
    }

    private Optional<FluentSelect> parseSelector(
            final ContentIterator iterator,
            final FluentContentParser contentParser,
            final FluentPlaceable placeable
    ) {
        if (iterator.character() != '-') {
            return Optional.empty();
        }

        if (iterator.nextChar() != '>') {
            throw new RuntimeException("Expected '->' but got '-" + Character.toString(iterator.character()) + "'");
        }

        iterator.nextChar();

        ParserUtil.skipWhitespace(iterator);

        if (iterator.character() != '\n') {
            throw new RuntimeException("Expected '\\n' but got '" + Character.toString(iterator.character()) + "'");
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        final List<FluentSelect.FluentVariant> variantList = new ArrayList<>();
        FluentSelect.FluentVariant defaultVariant = null;

        while (true) {
            boolean isDefault = false;

            if (iterator.character() == '*') {
                if (defaultVariant != null) {
                    throw new RuntimeException("Only one default variant can exist.");
                }

                isDefault = true;

                iterator.nextChar();
            }

            final Optional<FluentSelect.FluentVariant> variant = parseVariant(iterator, contentParser);

            if (variant.isEmpty()) {
                break;
            }

            variantList.add(variant.get());

            if (isDefault) {
                defaultVariant = variant.get();
            }
        }

        if (defaultVariant == null) {
            throw new RuntimeException("There needs to be at lease one default variant");
        }

        return Optional.of(new FluentSelectExpression(placeable, variantList));
    }

    private Optional<FluentSelect.FluentVariant> parseVariant(final ContentIterator iterator, final FluentContentParser contentParser) {
        if (iterator.character() != '[') {
            return Optional.empty();
        }

        iterator.nextChar();

        ParserUtil.skipWhitespace(iterator);

        final String variantKey = getVariantKey(iterator);

        ParserUtil.skipWhitespace(iterator);

        if (iterator.character() != ']') {
            throw new RuntimeException("Expected ']' but got '" + Character.toString(iterator.character()) + "'");
        }

        iterator.nextChar();

        final List<FluentPattern> content = contentParser.parse(iterator, contentIterator -> {
            ParserUtil.skipWhitespace(contentIterator);

            final int character = contentIterator.character();
            return character == '[' || character == '*' || character == '}';
        });

        return Optional.of(new FluentSelectExpression.FluentVariant(variantKey, content));
    }

    private String getVariantKey(final ContentIterator iterator) {
        final Optional<String> number = FluentNumberLiteralParser.parseNumberLiteral(iterator);
        if (number.isPresent()) {
            return number.get();
        }
        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);

        if (identifier.isEmpty()) {
            throw new RuntimeException("Expected identifier");
        }

        return identifier.get();
    }

}
