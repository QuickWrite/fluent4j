package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.impl.parser.pattern.FluentPlaceableParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;

public final class PlaceableParserBuilder {
    private PlaceableParserBuilder() {}

    public static PlaceableParser.Builder builder() {
        return FluentPlaceableParser.builder();
    }

    public static PlaceableParser defaultParser() {
        return FluentPlaceableParser.DEFAULT;
    }
}
