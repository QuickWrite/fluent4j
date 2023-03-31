package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.impl.parser.pattern.FluentContentParserGroup;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class ContentParserBuilder {
    private ContentParserBuilder() {}

    public static <B extends ResultBuilder> FluentContentParser.Builder<B> builder() {
        return FluentContentParserGroup.builder();
    }

    @SuppressWarnings("unchecked")
    public static <B extends ResultBuilder> FluentContentParser<B> defaultParser() {
        return (FluentContentParser<B>) FluentContentParserGroup.DEFAULT;
    }
}
