package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.impl.parser.FluentParserGroup;
import net.quickwrite.fluent4j.iterator.ContentIterator;

public interface FluentResourceParser {
    FluentResourceParser DEFAULT_PARSER = FluentParserGroup.getBasicParser();

    FluentResource parse(final ContentIterator iterator);
}
