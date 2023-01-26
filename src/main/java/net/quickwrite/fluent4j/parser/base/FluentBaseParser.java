package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.stream.ContentStream;

public interface FluentBaseParser {
    ParseResult<?> tryParse(final ContentStream content);
}
