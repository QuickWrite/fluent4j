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

    /**
     * The interface to declare that the object
     * can be sanitized and implements custom logic for
     * that exact step.
     */
    interface Sanitizable {
        /**
         * Adds the element to the current result.
         *
         * @param index The position the element is az in the unsanitized list
         * @param start The position that the sanitization started at.
         * @param unsanitizedPatternList The list that is being generated from the previous steps
         * @param builder The builder that is being used for the sanitization step
         * @param whitespace The amount of whitespace that was being calculated
         */
        void sanitize(
                final int index,
                final int start,
                final List<FluentPattern> unsanitizedPatternList,
                final ListBuilder builder,
                final int whitespace
        );
    }

    /**
     * The builder for the sanitized list
     * of elements for the content.
     *
     * <p>
     *     This step is being done as it
     *     allows for better performance and
     *     better handling of the data at runtime.
     * </p>
     */
    interface ListBuilder {
        /**
         * Returns the list that is currently being operated upon.
         *
         * @return The current list of items
         */
        List<FluentPattern> currentList();

        /**
         * Appends a string towards the current string builder.
         *
         * @param charSequence The characters that should be added
         * @return The ListBuilder itself
         */
        ListBuilder appendString(final CharSequence charSequence);

        /**
         * Appends a character towards the current string builder.
         *
         * @param character The character that should be added
         * @return The ListBuilder itself
         */
        ListBuilder appendString(final char character);

        /**
         * Adds an element to the current list and flushes
         * the string to ensure the correct order of elements.
         *
         * @param pattern The pattern that should be added.
         * @return The ListBuilder itself
         */
        ListBuilder appendElement(final FluentPattern pattern);

        /**
         * Adds the current string that is being built
         * to the list of elements.
         *
         * <p>
         *     If the current string is empty it won't
         *     get added as a new item.
         * </p>
         */
        void flushString();
    }

    interface Builder extends net.quickwrite.fluent4j.util.Builder<FluentContentParser> {
        Builder addParser(final FluentPatternParser<? extends FluentPattern> parser);
    }
}
