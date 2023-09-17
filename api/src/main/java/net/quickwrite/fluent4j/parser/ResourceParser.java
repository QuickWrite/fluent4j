package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;

/**
 * The parser that is parsing an entire Resource from start to end.
 */
public interface ResourceParser extends FluentParser<FluentResource> {

    interface Builder extends net.quickwrite.fluent4j.util.Builder<ResourceParser> {
        Builder addParser(final FluentElementParser<? extends FluentEntry> parser);

        default Builder addParser(final FluentElementParser.FluentElementParserList parser) {
            return addParser(parser.getParser());
        }
    }
}
