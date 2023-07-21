package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The parser that is parsing an entire Resource from start to end.
 *
 * @param <B> The Builder that is being used to parse this resource
 */
public interface ResourceParser<B extends ResultBuilder> extends FluentParser<FluentResource<B>> {

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<ResourceParser<B>> {
        Builder<B> addParser(final FluentElementParser<? extends FluentEntry<B>> parser);

        default Builder<B> addParser(final FluentElementParser.FluentElementParserList parser) {
            return addParser(parser.getParser());
        }
    }
}
