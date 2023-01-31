package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public interface PlaceableExpressionParser<T extends FluentPlaceable> {
    ParseResult<T> parse(final ContentIterator iterator, final PlaceableParser placeableParser);
}
