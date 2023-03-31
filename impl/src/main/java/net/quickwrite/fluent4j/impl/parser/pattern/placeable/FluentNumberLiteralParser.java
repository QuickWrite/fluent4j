package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public class FluentNumberLiteralParser<B extends ResultBuilder> implements PlaceableExpressionParser<B> {
    public static Optional<String> parseNumberLiteral(final ContentIterator iterator) {
        if (iterator.character() == '-' && !isDigit(iterator.nextChar())) {
            return Optional.empty();
        }

        if (!isDigit(iterator.character())) {
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
    public Optional<FluentPlaceable<B>> parse(final ContentIterator iterator, final PlaceableParser<B> placeableParser) {
        final Optional<String> number = parseNumberLiteral(iterator);

        return number.map(FluentNumberLiteral::new);
    }
}
