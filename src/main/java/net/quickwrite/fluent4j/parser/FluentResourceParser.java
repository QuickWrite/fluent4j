package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.stream.ContentStream;

public interface FluentResourceParser {
    FluentResource parse(final ContentStream stream);
}
