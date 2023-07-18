package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.function.Function;

/**
 * A list of parsers for parsing the contents of a message
 *
 * @param <B> The Builder that is being used to parse this resource
 */
public interface FluentContentParser<B extends ResultBuilder> {
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
    List<FluentPattern<B>> parse(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker);

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<FluentContentParser<B>> {
        Builder<B> addParser(final FluentPatternParser<? extends FluentPattern<B>, B> parser);
    }
}
