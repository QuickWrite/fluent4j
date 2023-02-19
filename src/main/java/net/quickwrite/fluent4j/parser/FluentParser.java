package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.impl.parser.FluentParserGroup;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentParser<T> {
    FluentParser<FluentResource<ResultBuilder>> RESOURCE_PARSER = FluentParserGroup.getBasicParser();

    T parse(final ContentIterator iterator);
}
