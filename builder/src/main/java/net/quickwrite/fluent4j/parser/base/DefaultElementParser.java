package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.impl.parser.base.CommentSkipper;
import net.quickwrite.fluent4j.impl.parser.base.WhitespaceSkipper;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentMessageParser;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentTermParser;
import net.quickwrite.fluent4j.parser.pattern.ContentParserBuilder;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;

public enum DefaultElementParser implements FluentElementParser.FluentElementParserList {
    WHITESPACE_SKIPPER(WhitespaceSkipper.DEFAULT),
    COMMENT_SKIPPER(CommentSkipper.DEFAULT),
    TERM_PARSER(getTermParser(ContentParserBuilder.defaultParser())),
    MESSAGE_PARSER(getMessageParser(ContentParserBuilder.defaultParser()));

    private final FluentElementParser<? extends FluentEntry> parser;

    DefaultElementParser(final FluentElementParser<? extends FluentEntry> parser) {
        this.parser = parser;
    }

    public FluentElementParser<? extends FluentEntry> getParser() {
        return parser;
    }

    public static FluentElementParser<? extends FluentEntry> getTermParser(
            final FluentContentParser contentParser
    ) {
        return new FluentTermParser(contentParser);
    }

    public static FluentElementParser<? extends FluentEntry> getMessageParser(
            final FluentContentParser contentParser
    ) {
        return new FluentMessageParser(contentParser);
    }
}
