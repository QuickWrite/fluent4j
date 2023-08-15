package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public final class WhitespaceSkipper implements FluentElementParser<FluentEntry> {
    public static WhitespaceSkipper DEFAULT = new WhitespaceSkipper();

    @Override
    public ParseResult<FluentEntry> parse(final ContentIterator content) {
        if (content.line().length() != 0 && !Character.isWhitespace(content.character())) {
            return ParseResult.failure();
        }

        /* @formatter:off */
        while (Character.isWhitespace(content.nextChar()));
        /* @formatter:on */

        return ParseResult.skip();
    }
}
