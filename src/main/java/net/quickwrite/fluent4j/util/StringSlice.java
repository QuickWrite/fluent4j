package net.quickwrite.fluent4j.util;

import com.ibm.icu.text.UTF16;

/**
 * A wrapper class for a String that has added functionality.
 * <p>
 * It is mainly storing the start and the end of the String
 * as integer values so that only a part of the string can
 * be used.<br>
 * This allows the creation of more StringSlices faster with
 * less Memory as it does not need to copy a part of a String
 * and does not need to store that String.
 * <p>
 * It has also an index and functions like a stream.
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
    public StringSlice(final String base) {
        this(base, 0, base.length());
    }

    /**
     * Creates a StringSlice from an already given
     * {@link String} with boundaries.
     *
     * <p>
     * This is mainly used so that there don't need to be
     * new Strings created.
     * </p>
     *
     * @param base  The base string
     * @param start The start of the StringSlice
     * @param end   The end of the StringSlice
     */
    protected StringSlice(final String base, final int start, final int end) {
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
     * Returns the UTF16 value of the current char as
     * a {@link String}
     *
     * @return The UTF16-Value
     */
    public String getCharUTF16() {
        if (isBigger())
            return "\0";

        return UTF16.valueOf(this.base, this.start + this.index);
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
     * Sets the index to the given integer
     *
     * @param index The integer the index should be set to.
     */
    public void setIndex(int index) {
        this.index = index;
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

    /**
     * Decrements the index by one.
     *
     * <p>
     * If the index is smaller than {@code 0} it will
     * be set to {@code 0}.
     */
    public void decrement() {
        decrement(1);
    }

    /**
     * Decrements the index by the amount
     * given in the parameter.
     *
     * <p>
     * If the index is smaller than {@code 0} it will
     * be set to {@code 0}.
     *
     * @param times The amount the {@link StringSlice} should decrement
     */
    public void decrement(final int times) {
        if (getPosition() - times < 0) {
            setIndex(0);
        }

        setIndex(getPosition() - times);
    }

    /**
     * Gets the char at the {@code current position + index}
     * position.
     * <br>
     * When the index is bigger than the length of the
     * StringSlice the output will be {@code '\0'}.
     *
     * @param index The amount of characters the method should jump over
     * @return The character
     */
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
     * Gets the String representation of the StringSlice.
     *
     * @return The StringSlice as a String
     */
    public String toString() {
        return this.base.substring(this.start, this.end);
    }
}
