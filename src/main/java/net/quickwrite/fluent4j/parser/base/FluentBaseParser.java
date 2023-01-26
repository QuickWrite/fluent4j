package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.iterator.ContentIterator;

public interface FluentBaseParser {
    ParseResult<?> tryParse(final ContentIterator content);
}
