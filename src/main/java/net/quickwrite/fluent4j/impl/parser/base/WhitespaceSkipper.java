package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public class WhitespaceSkipper implements FluentParser<FluentEntry> {
    @Override
    public ParseResult<FluentEntry> tryParse(final ContentIterator content) {
        if (content.line().length() != 0 && !Character.isWhitespace(content.character())) {
            return ParseResult.failure();
        }

        while (Character.isWhitespace(content.nextChar()));

        return ParseResult.skip();
    }
}
