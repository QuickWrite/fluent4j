package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.impl.parser.pattern.FluentPlaceableParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;

public final class PlaceableParserBuilder {
    private static final PlaceableParser DEFAULT = builder()
            .addParser(DefaultPlaceableParser.STRING)
            .addParser(DefaultPlaceableParser.NUMBER)
            .addParser(DefaultPlaceableParser.FUNCTION)
            .addParser(DefaultPlaceableParser.TERM_REFERENCE)
            .addParser(DefaultPlaceableParser.MESSAGE_REFERENCE)
            .addParser(DefaultPlaceableParser.VARIABLE)
            .build();

    private PlaceableParserBuilder() {}

    public static PlaceableParser.Builder builder() {
        return FluentPlaceableParser.builder();
    }

    public static PlaceableParser defaultParser() {
        return DEFAULT;
    }
}
