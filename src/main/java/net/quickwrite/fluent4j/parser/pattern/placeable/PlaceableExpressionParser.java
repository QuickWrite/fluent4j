package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public interface PlaceableExpressionParser<T, B extends ResultBuilder> {
    Optional<T> parse(final ContentIterator iterator, final PlaceableParser<B> placeableParser);
}
