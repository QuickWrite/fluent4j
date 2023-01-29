package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public interface FluentParser<T> {
    ParseResult<T> tryParse(final ContentIterator content);
}
