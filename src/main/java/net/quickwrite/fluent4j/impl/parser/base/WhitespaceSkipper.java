package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class WhitespaceSkipper<B extends ResultBuilder> implements FluentElementParser<FluentEntry<B>> {
    @Override
    public ParseResult<FluentEntry<B>> parse(final ContentIterator content) {
        if (content.line().length() != 0 && !Character.isWhitespace(content.character())) {
            return ParseResult.failure();
        }

        while (Character.isWhitespace(content.nextChar()));

        return ParseResult.skip();
    }
}
