package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.Optional;

public interface PlaceableExpressionParser<T extends FluentPlaceable> {
    Optional<T> parse(final ContentIterator iterator, final PlaceableParser placeableParser);
}
