package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;

import java.util.Optional;

public class FluentNumberLiteralParser implements PlaceableExpressionParser {
    public static Optional<String> parseNumberLiteral(final ContentIterator iterator) {
        final int start = iterator.position()[1];

        if (iterator.character() == '-' && !isDigit(iterator.nextChar())) {
            return Optional.empty();
        }

        if (!isDigit(iterator.character())) {
            return Optional.empty();
        }

        /* @formatter:off */
        while (isDigit(iterator.nextChar()));
        /* @formatter:on */

        if (iterator.character() != '.') {
            return Optional.of(iterator.line().substring(start, iterator.position()[1]));
        }

        /* @formatter:off */
        while (isDigit(iterator.nextChar()));
        /* @formatter:on */

        return Optional.of(iterator.line().substring(start, iterator.position()[1]));
    }

    private static boolean isDigit(final int character) {
        return '0' <= character && character <= '9';
    }

    @Override
    public Optional<FluentPlaceable> parse(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final Optional<String> number = parseNumberLiteral(iterator);

        return number.map(FluentNumberLiteral::new);
    }
}
