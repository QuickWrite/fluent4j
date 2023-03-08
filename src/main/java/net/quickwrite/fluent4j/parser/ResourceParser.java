package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.impl.parser.FluentParserGroup;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface ResourceParser<B extends ResultBuilder> extends FluentParser<FluentResource<B>> {
    ResourceParser<ResultBuilder> DEFAULT = FluentParserGroup.getBasicParser();

    static <B extends ResultBuilder> Builder<B> builder() {
        return FluentParserGroup.builder();
    }

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<ResourceParser<B>> {
        Builder<B> addParser(final FluentElementParser<? extends FluentEntry<B>> parser);
    }
}
