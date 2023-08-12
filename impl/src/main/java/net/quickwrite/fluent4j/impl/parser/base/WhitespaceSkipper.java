package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public final class WhitespaceSkipper implements FluentElementParser<FluentEntry> {
    @Override
    public ParseResult<FluentEntry> parse(final ContentIterator content) {
        if (content.line().length() != 0 && !Character.isWhitespace(content.character())) {
            return ParseResult.failure();
        }

        while (Character.isWhitespace(content.nextChar()));

        return ParseResult.skip();
    }
}
