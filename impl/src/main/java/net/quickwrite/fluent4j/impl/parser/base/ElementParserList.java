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
    public static final FluentElementParser<FluentEntry>
            WHITESPACE_SKIPPER = new WhitespaceSkipper(),
            COMMENT_SKIPPER = new CommentSkipper(),
            TERM_PARSER = (FluentElementParser<FluentEntry>) getTermParser(FluentContentParserGroup.DEFAULT),
            MESSAGE_PARSER = (FluentElementParser<FluentEntry>) getMessageParser(FluentContentParserGroup.DEFAULT);

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
