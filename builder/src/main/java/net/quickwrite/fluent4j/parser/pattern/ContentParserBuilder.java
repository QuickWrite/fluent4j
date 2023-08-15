package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.impl.parser.pattern.FluentContentParserGroup;

public final class ContentParserBuilder {
    private static final FluentContentParser DEFAULT = builder()
            .addParser(PlaceableParserBuilder.defaultParser())
            .build();

    private ContentParserBuilder() {}

    public static FluentContentParser.Builder builder() {
        return FluentContentParserGroup.builder();
    }

    public static FluentContentParser defaultParser() {
        return DEFAULT;
    }
}
