package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;

import java.util.Optional;

public interface PlaceableParser extends FluentPatternParser<FluentPattern> {
    Optional<FluentPlaceable> parsePlaceable(final ContentIterator iterator);
}
