package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public interface FluentPatternParser<T> {
    int getStartingChar();

    ParseResult<T> parse(final ContentIterator iterator, final FluentContentParser contentParser);
}
