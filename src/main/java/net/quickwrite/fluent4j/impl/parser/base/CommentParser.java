package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentBaseParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public class CommentParser implements FluentBaseParser {
    @Override
    public ParseResult<?> tryParse(final ContentIterator content) {
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

        final String text = content.line();
        content.nextLine();

        return ParseResult.success(text);
    }
}
