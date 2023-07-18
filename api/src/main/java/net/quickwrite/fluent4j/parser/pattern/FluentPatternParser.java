package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The basic pattern parser interface
 *
 * <p>
 *     A pattern itself is a component of the content
 *     of a message. E. g.:
 *     <pre>
 *         message = pattern { $pattern2 }
 *     </pre>
 * </p>
 *
 * @param <T> The resulting pattern
 * @param <B> The Builder that is being used to parse this resource
 */
public interface FluentPatternParser<T extends FluentPattern<B>, B extends ResultBuilder> {
    /**
     * Returns the starting character of the component
     *
     * @return The starting char
     */
    int getStartingChar();

    /**
     * Returns the parsed version of the next iterator
     * sequence for the specific pattern.
     *
     * @param iterator The iterator that should be parsed
     * @param contentParser The parser that can be used to
     *                      recursively parse the content like
     *                      the message content
     * @return The result of the parsing operation
     */
    ParseResult<T> parse(final ContentIterator iterator, final FluentContentParser<B> contentParser);
}
