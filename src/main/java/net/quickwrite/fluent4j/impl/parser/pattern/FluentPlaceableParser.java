package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.FluentNumberLiteralParser;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.FluentStringLiteralParser;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.FluentVariableReferenceParser;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;

public class FluentPlaceableParser implements FluentPatternParser<FluentPattern> {
    private final List<PlaceableExpressionParser<? extends FluentPattern>> expressionParserList = new ArrayList<>();

    public static FluentPlaceableParser getBasicParser() {
        final FluentPlaceableParser base = new FluentPlaceableParser();

        base.addParser(new FluentStringLiteralParser());
        base.addParser(new FluentNumberLiteralParser());

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

        skipWhitespace(iterator);

        FluentPattern placeable = null;

        final int[] position = iterator.position();
        boolean canSelect = false;

        for (final PlaceableExpressionParser<? extends FluentPattern> expressionParser : expressionParserList) {
            final ParseResult<? extends FluentPattern> parseResult = expressionParser.parse(iterator);

            switch (parseResult.getType()) {
                case FAILURE:
                    iterator.setPosition(position);
                    continue;
                case SKIP:
                    throw new RuntimeException("A PlaceableExpressionParser cannot return SKIP");
            }

            canSelect = expressionParser.canSelectExpression();
            placeable = parseResult.getValue();
            break;
        }

        if (placeable == null) {
            throw new RuntimeException("All PlaceableExpressionParsers returned FAILURE");
        }

        skipWhitespace(iterator);

        if (iterator.character() != '}') {
            throw new RuntimeException("Expected '}' but got '" + Character.toString(iterator.character()) + "'");
        }

        iterator.nextChar();

        return ParseResult.success(placeable);
    }

    private void skipWhitespace(final ContentIterator iterator) {
        while (Character.isWhitespace(iterator.character())) {
            iterator.nextChar();
        }
    }
}
