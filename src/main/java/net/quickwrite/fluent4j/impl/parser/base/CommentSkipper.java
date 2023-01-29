package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public final class CommentSkipper implements FluentElementParser<FluentEntry> {
    @Override
    public ParseResult<FluentEntry> parse(final ContentIterator content) {
        for (int i = 0; i < 3; i++) {
            if (content.character() != '#') {
                if (i == 0) {
                    return ParseResult.failure();
                }

                break;
            }

            content.nextChar();
        }

        if (!Character.isWhitespace(content.character())) {
            // TODO: better exception
            throw new RuntimeException();
        }

        content.nextLine();

        return ParseResult.skip();
    }
}
