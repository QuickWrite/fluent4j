package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.impl.parser.FluentParserGroup;

public final class ResourceParserBuilder {
    private ResourceParserBuilder() {}

    public static ResourceParser.Builder builder() {
        return FluentParserGroup.builder();
    }

    public static ResourceParser defaultParser() {
        return FluentParserGroup.DEFAULT;
    }
}
