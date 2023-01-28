package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public interface FluentBaseParser {
    ParseResult<FluentEntry> tryParse(final ContentIterator content);
}
