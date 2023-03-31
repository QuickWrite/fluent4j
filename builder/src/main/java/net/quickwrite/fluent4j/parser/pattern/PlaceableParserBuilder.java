package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.impl.parser.pattern.FluentPlaceableParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class PlaceableParserBuilder {
    private PlaceableParserBuilder() {}

    public static <B extends ResultBuilder> PlaceableParser.Builder<B> builder() {
        return FluentPlaceableParser.builder();
    }

    @SuppressWarnings("unchecked")
    public static <B extends ResultBuilder> PlaceableParser<B> defaultParser() {
        return (PlaceableParser<B>) FluentPlaceableParser.DEFAULT;
    }
}
