package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public class FluentNumberLiteralParser implements PlaceableExpressionParser<FluentNumberLiteral> {
    @Override
    public ParseResult<FluentNumberLiteral> parse(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final ParseResult<String> number = parseNumberLiteral(iterator);

        if (number.getType() == ParseResult.ParseResultType.FAILURE) {
            return ParseResult.failure();
        }

        return ParseResult.success(new FluentNumberLiteral(number.getValue()));
    }

    public static ParseResult<String> parseNumberLiteral(final ContentIterator iterator) {
        if (!isDigit(iterator.character()) && iterator.character() != '-') {
            return ParseResult.failure();
        }

        final int start = iterator.position()[1];

        while (isDigit(iterator.nextChar()));

        if (iterator.character() != '.') {
            return ParseResult.success(iterator.line().substring(start, iterator.position()[1]));
        }

        while (isDigit(iterator.nextChar()));

        return ParseResult.success(iterator.line().substring(start, iterator.position()[1]));
    }

    private static boolean isDigit(final int character) {
        return '0' <= character && character <= '9';
    }
}
