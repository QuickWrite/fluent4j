package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.stream.ContentStream;

import java.util.Optional;

public interface FluentBaseParser {
    Optional<?> tryParse(final ContentStream content);
}
