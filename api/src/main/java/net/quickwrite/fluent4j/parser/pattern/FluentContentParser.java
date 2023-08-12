package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.List;

/**
 * A list of parsers for parsing the contents of a message
 */
public interface FluentContentParser {
    /**
     * Parses the content of an element into a list of {@link FluentPattern}
     * until it reaches the end of the input determined either by the
     * {@code iterator} or by the {@code endChecker}.
     *
     * @param iterator The iterator that the parser should run over
     * @param endChecker The function that determines if the current
     *                   state of the iterator is defined as the end
     * @return A list of the parsed {@link FluentPattern}
     */
    List<FluentPattern> parse(final ContentIterator iterator, final EndChecker endChecker);

    /**
     * The functional interface that determines if the
     * current state of the iterator is the end of the content
     * that should be parsed.
     */
    interface EndChecker {
        boolean check(final ContentIterator iterator);
    }

    interface Builder extends net.quickwrite.fluent4j.util.Builder<FluentContentParser> {
        Builder addParser(final FluentPatternParser<? extends FluentPattern> parser);
    }
}
