package net.quickwrite.fluent4j.parser.base;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.parser.FluentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The parser for the basis of the file.
 * They are parsing the topdown elements that are directly
 * being used in the {@link net.quickwrite.fluent4j.container.FluentBundle}.
 *
 * <p>
 * These parsers are for example parsing the comments,
 * messages, terms, etc.
 * </p>
 *
 * @param <T>
 */
public interface FluentElementParser<T> extends FluentParser<ParseResult<T>> {
    /**
     * An interface for providing a list as an easy bridge
     * between implementation and the interface. It can be used
     * to simplify the building step of the parser.
     */
    interface FluentElementParserList {
        FluentElementParser<? extends FluentEntry> getParser();
    }
}
