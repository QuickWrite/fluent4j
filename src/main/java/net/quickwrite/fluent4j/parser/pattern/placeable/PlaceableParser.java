package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public interface PlaceableParser<B extends ResultBuilder> extends FluentPatternParser<FluentPattern<B>, B> {
    Optional<FluentPlaceable<B>> parsePlaceable(final ContentIterator iterator);
}
