package net.quickwrite.fluent4j.result;

/**
 * The ResultBuilder interface is represents a way to
 * combine all the different elements of a message for a
 * simple result.
 */
public interface ResultBuilder {
    /**
     * Appends a sequence to the result.
     *
     * @param sequence the sequence to append
     * @return the ResultBuilder instance
     */
    ResultBuilder append(final CharSequence sequence);

    /**
     * Appends a character to the result.
     *
     * @param character the character to append
     * @return the ResultBuilder instance
     */
    ResultBuilder append(final char character);

    /**
     * Appends a Unicode code point as a character to the result.
     *
     * @param codePoint the Unicode code point representing a character to append
     * @return the ResultBuilder instance
     */
    ResultBuilder append(final int codePoint);

    /**
     * Retrieves a simple builder for accumulating strings in a tree traversal.
     *
     * @param <T> the type of the simple builder
     * @return the simple builder instance
     */
    <T extends ResultBuilder> T getSimpleBuilder();
}
