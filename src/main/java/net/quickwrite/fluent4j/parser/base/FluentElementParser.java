package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.impl.parser.base.CommentSkipper;
import net.quickwrite.fluent4j.impl.parser.base.WhitespaceSkipper;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentMessageParser;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentTermParser;
import net.quickwrite.fluent4j.parser.FluentParser;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentElementParser<T> extends FluentParser<ParseResult<T>> {
    static <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getTermParser(
            final FluentContentParser<B> contentParser
    ) {
        return new FluentTermParser<>(contentParser);
    }

    static <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getMessageParser(
            final FluentContentParser<B> contentParser
    ) {
        return new FluentMessageParser<>(contentParser);
    }

    enum DefaultParser {
        WHITESPACE_SKIPPER(new WhitespaceSkipper<>()),
        COMMENT_SKIPPER(new CommentSkipper<>()),
        TERM_PARSER(getTermParser(FluentContentParser.DEFAULT_PARSER)),
        MESSAGE_PARSER(getMessageParser(FluentContentParser.DEFAULT_PARSER));

        private final FluentElementParser<? extends FluentEntry<? extends ResultBuilder>> parser;

        DefaultParser(final FluentElementParser<? extends FluentEntry<? extends ResultBuilder>> parser) {
            this.parser = parser;
        }

        @SuppressWarnings("unchecked")
        public <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getParser() {
            return (FluentElementParser<? extends FluentEntry<B>>) parser;
        }
    }
}
