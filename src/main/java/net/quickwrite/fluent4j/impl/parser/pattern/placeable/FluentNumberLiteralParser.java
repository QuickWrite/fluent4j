package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;

import java.util.Optional;

public class FluentNumberLiteralParser implements PlaceableExpressionParser<FluentNumberLiteral> {
    public static Optional<String> parseNumberLiteral(final ContentIterator iterator) {
        if (!isDigit(iterator.character()) && iterator.character() != '-') {
            return Optional.empty();
        }

        final int start = iterator.position()[1];

        while (isDigit(iterator.nextChar()));

        if (iterator.character() != '.') {
            return Optional.of(iterator.line().substring(start, iterator.position()[1]));
        }

        while (isDigit(iterator.nextChar()));

        return Optional.of(iterator.line().substring(start, iterator.position()[1]));
    }

    private static boolean isDigit(final int character) {
        return '0' <= character && character <= '9';
    }

    @Override
    public Optional<FluentNumberLiteral> parse(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final Optional<String> number = parseNumberLiteral(iterator);

        return number.map(FluentNumberLiteral::new);
    }
}
