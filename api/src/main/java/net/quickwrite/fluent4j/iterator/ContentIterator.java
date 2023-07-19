package net.quickwrite.fluent4j.iterator;

/**
 * The main iterator that the parsers are traversing
 *
 * <p>
 *     This is being used as an abstraction for the
 *     input of the parser as the input could be
 *     everything from a File to a String.
 * </p>
 */
public interface ContentIterator {
    /**
     * Returns the name of the input type and
     * other information for more informational
     * Exception messages.
     *
     * @return The name of the input
     */
    String inputName();

    /**
     * Returns the current char as a codepoint
     * that the iterator currently is at.
     *
     * @return The current character
     */
    int character();

    /**
     * Returns the next char as a codepoint
     * and increments the iterator.
     *
     * @return The next char
     */
    int nextChar();

    /**
     * Returns the current line that the
     * input currently is at.
     *
     * @return The current line
     */
    String line();

    /**
     * Returns the next line that the input
     * is currently at and increments the
     * iterator accordingly.
     *
     * @return The next line
     */
    String nextLine();

    /**
     * Returns the current position based on
     * the current line and the current line
     * position.
     *
     * @return The current position
     */
    int[] position();

    /**
     * Sets the current position based on the
     * line and the current line position
     *
     * @param position The position that the iterator should
     *                 be changed to
     */
    void setPosition(final int[] position);
}
