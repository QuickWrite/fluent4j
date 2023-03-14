package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentMessageParser;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentTermParser;
import net.quickwrite.fluent4j.impl.parser.pattern.FluentContentParserGroup;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class ElementParserList {
    private ElementParserList() {}

    @SuppressWarnings("unchecked")
    public static final FluentElementParser<FluentEntry<ResultBuilder>>
            WHITESPACE_SKIPPER = new WhitespaceSkipper<>(),
            COMMENT_SKIPPER = new CommentSkipper<>(),
            TERM_PARSER = (FluentElementParser<FluentEntry<ResultBuilder>>) getTermParser(FluentContentParserGroup.DEFAULT),
            MESSAGE_PARSER = (FluentElementParser<FluentEntry<ResultBuilder>>) getMessageParser(FluentContentParserGroup.DEFAULT);

    public static <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getTermParser(
            final FluentContentParser<B> contentParser
    ) {
        return new FluentTermParser<>(contentParser);
    }

    public static <B extends ResultBuilder> FluentElementParser<? extends FluentEntry<B>> getMessageParser(
            final FluentContentParser<B> contentParser
    ) {
        return new FluentMessageParser<>(contentParser);
    }
}
