package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentPatternParser<T extends FluentPattern<B>, B extends ResultBuilder> {
    int getStartingChar();

    ParseResult<T> parse(final ContentIterator iterator, final FluentContentParser<B> contentParser);
}
