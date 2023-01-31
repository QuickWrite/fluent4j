package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.FluentFunctionParser;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.FluentNumberLiteralParser;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.FluentStringLiteralParser;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.FluentVariableReferenceParser;
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

        final Optional<FluentPlaceable> placeable = parsePlaceable(iterator);

        if (placeable.isEmpty()) {
            throw new RuntimeException("All PlaceableExpressionParsers returned FAILURE");
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        if (iterator.character() != '}') {
            throw new RuntimeException("Expected '}' but got '" + Character.toString(iterator.character()) + "'");
        }

        iterator.nextChar();

        return ParseResult.success(placeable.get());
    }

    @Override
    public Optional<FluentPlaceable> parsePlaceable(final ContentIterator iterator) {
        final int[] position = iterator.position();

        for (final PlaceableExpressionParser<? extends FluentPlaceable> expressionParser : expressionParserList) {
            final ParseResult<? extends FluentPlaceable> parseResult = expressionParser.parse(iterator, this);

            switch (parseResult.getType()) {
                case FAILURE:
                    iterator.setPosition(position);
                    continue;
                case SKIP:
                    throw new RuntimeException("A PlaceableExpressionParser cannot return SKIP");
            }

            return Optional.of(parseResult.getValue());
        }

        return Optional.empty();
    }
}
