package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.impl.parser.base.CommentSkipper;
import net.quickwrite.fluent4j.impl.parser.base.ElementParserList;
import net.quickwrite.fluent4j.impl.parser.base.WhitespaceSkipper;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentMessageParser;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentTermParser;
import net.quickwrite.fluent4j.parser.pattern.ContentParserBuilder;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

public enum DefaultElementParser {
    WHITESPACE_SKIPPER(new WhitespaceSkipper<>()),
    COMMENT_SKIPPER(new CommentSkipper<>()),
    TERM_PARSER(getTermParser(ContentParserBuilder.defaultParser())),
    MESSAGE_PARSER(getMessageParser(ContentParserBuilder.defaultParser()));

    private final FluentElementParser<? extends FluentEntry<? extends ResultBuilder>> parser;

    DefaultElementParser(final FluentElementParser<? extends FluentEntry<? extends ResultBuilder>> parser) {
        this.parser = parser;
    }

    @SuppressWarnings("unchecked")
    public <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getParser() {
        return (FluentElementParser<? extends FluentEntry<B>>) parser;
    }

    public static <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getTermParser(
            final FluentContentParser<B> contentParser
    ) {
        return ElementParserList.getTermParser(contentParser);
    }

    public static <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getMessageParser(
            final FluentContentParser<B> contentParser
    ) {
        return ElementParserList.getMessageParser(contentParser);
    }
}
