package net.quickwrite.fluent4j.util;

/**
 * A wrapper class for a String that has added functionality.
 * <p>
 * It is mainly storing the start and the end of the String
 * as integer values so that only a part of the string can
 * be used.<br>
 * This allows the creation of more StringSlices faster with
 * less Memory as it does not need to copy a part of a String
 * and does not need to store that String.
 * </p>
 * <p>
 * It has also an index and functions like a stream.
 * </p>
 */
public class StringSlice {
    private final String base;
    private final int start;
    private final int end;

    private int index;

    /**
     * Initializes the first StringSlice with a String
     * and the start position as 0 and the end position
     * as the length of the String.
     *
     * @param base The String that the StringSlice is using
     */
    public StringSlice(String base) {
        this(base, 0, base.length());
    }

    protected StringSlice(String base, int start, int end) {
        this.base = base;
        this.start = start;

        this.end = Math.min(end, base.length());

        this.index = 0;
    }

    /**
     * Gets the char at the current position the index is
     * currently at.
     * <br>
     * When the index is bigger than the length of the
     * StringSlice the output will be {@code '\0'}.
     *
     * @return char
     */
    public char getChar() {
        if (isBigger())
            return '\0';

        return this.base.charAt(this.start + this.index);
    }

    /**
     * Checks if the current index is bigger than the
     * length of the StringSlice itself.
     *
     * @return True if it is bigger<br>
     * False if it is smaller
     */
    public boolean isBigger() {
        return index >= end - start;
    }

    /**
     * Gets the current position the index is currently
     * at.
     *
     * @return The integer index
     */
    public int getPosition() {
        return this.index;
    }

    /**
     * Gets the current position in the whole
     * String.
     *
     * @return The integer index
     */
    public int getAbsolutePosition() {
        return this.start + this.index;
    }

    /**
     * Increments the index with one. <br>
     * Except when the index is larger than the length
     * of the StringSlice.
     */
    public void increment() {
        if (isBigger())
            return;

        this.index++;
    }

    public void decrement() {
        this.index--;
    }

    public char peek(int index) {
        if (getPosition() + start + index > end) {
            return '\0';
        }

        return this.base.charAt(getPosition() + start + index);
    }

    /**
     * Returns a new StringSlice that has only a part
     * of the current StringSlice that reaches from the start
     * to the end.
     *
     * @param start The start position the StringSlice should start
     * @param end   The end position the StringSlice should end
     * @return StringSlice
     */
    public StringSlice substring(int start, int end) {
        return new StringSlice(this.base, start + this.start, this.start + end);
    }

    /**
     * Gets the length of the StringSlice.
     *
     * @return length as an integer
     */
    public int length() {
        return this.end - this.start + 1;
    }

    /**
     * Sets the index to the given integer
     *
     * @param index The integer the index should be set to.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets the String representation of the StringSlice.
     *
     * @return The StringSlice as a String
     */
    public String toString() {
        return this.base.substring(this.start, this.end);
    }
}
