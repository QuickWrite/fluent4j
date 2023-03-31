package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.parser.FluentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentElementParser<T> extends FluentParser<ParseResult<T>> {
    interface FluentElementParserList {
        <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getParser();
    }
}
