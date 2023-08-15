package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

/**
 * The parser for the actual different Placeables.
 *
 * <p>
 *     This parser should only parse from the actual start of
 *     the element inside of the Placeable and not the start of
 *     the placeable itself.
 *     <br />
 *     For example with the placeable {@code { 42 } } it would
 *     parse from the {@code 4} to the {@code }} and close it.
 * </p>
 */
public interface PlaceableExpressionParser {
    Optional<FluentPlaceable> parse(final ContentIterator iterator, final PlaceableParser placeableParser);

    interface PlaceableExpressionParserList {
        PlaceableExpressionParser getParser();
    }
}
