package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.impl.parser.FluentParserGroup;
import net.quickwrite.fluent4j.parser.base.DefaultElementParser;

public final class ResourceParserBuilder {
    private static final ResourceParser DEFAULT = builder()
            .addParser(DefaultElementParser.WHITESPACE_SKIPPER)
            .addParser(DefaultElementParser.COMMENT_SKIPPER)
            .addParser(DefaultElementParser.TERM_PARSER)
            .addParser(DefaultElementParser.MESSAGE_PARSER)
            .build();

    private ResourceParserBuilder() {}

    public static ResourceParser.Builder builder() {
        return FluentParserGroup.builder();
    }

    public static ResourceParser defaultParser() {
        return DEFAULT;
    }
}
