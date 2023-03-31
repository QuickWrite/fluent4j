package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.impl.parser.FluentParserGroup;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class ResourceParserBuilder {
    private ResourceParserBuilder() {}

    public static <B extends ResultBuilder> ResourceParser.Builder<B> builder() {
        return FluentParserGroup.builder();
    }

    @SuppressWarnings("unchecked")
    public static <B extends ResultBuilder> ResourceParser<B> defaultParser() {
        return (ResourceParser<B>) FluentParserGroup.DEFAULT;
    }
}
