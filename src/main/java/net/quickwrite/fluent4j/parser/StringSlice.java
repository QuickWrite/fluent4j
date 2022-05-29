package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.ast.FluentPlaceable;
import net.quickwrite.fluent4j.ast.wrapper.FluentTextElement;
import net.quickwrite.fluent4j.exception.FluentParseException;

/**
 * A wrapper class for a String that has added functionality.
 * <p>
 *     It is mainly storing the start and the end of the String
 *     as integer values so that only a part of the string can
 *     be used.<br>
 *     This allows the creation of more StringSlices faster with
 *     less Memory as it does not need to copy a part of a String
 *     and does not need to store that String.
 * </p>
 * <p>
 *     It has also an index and functions like a stream.
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
     *         False if it is smaller
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
     * Increments the index with one. <br>
     * Except when the index is larger than the length
     * of the StringSlice.
     */
    public void increment() {
        if (isBigger())
            return;

        this.index++;
    }

    private void decrement() {
        this.index--;
    }

    public char peek(int index) {
        return this.base.charAt(getPosition() + start + index);
    }

    /**
     * Returns a new StringSlice that has only a part
     * of the current StringSlice that reaches from the start
     * to the end.
     *
     * @param start The start position the StringSlice should start
     * @param end The end position the StringSlice should end
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
     * Skips whitespace in the StringSlice. <br>
     *
     * (Only includes spaces and does not consider TAB) <br>
     *
     * It is incrementing the index until there is a part of
     * the StringSlice that does have something else than
     * Whitespace.
     *
     * @return If whitespace could be skipped.
     */
    public boolean skipWhitespace() {
        if(getChar() != ' ' && !(index >= length())) {
            return false;
        }

        do {
            increment();
        } while(getChar() == ' ' && !(index >= length()));

        return true;
    }

    /**
     * Skips whitespace and newlines in the StringSlice.
     *
     * (Only includes spaces and newlines and does not
     * consider TAB) <br>
     *
     * It is incrementing the index until there is a part of
     * the StringSlice that does have something else than
     * Whitespace or newline.
     *
     * @return If something could be skipped.
     */
    public boolean skipWhitespaceAndNL() {
        if(getChar() != ' ' && getChar() != '\n' || isBigger()) {
            return false;
        }

        do {
            increment();
        } while((getChar() == ' ' || getChar() == '\n') && !isBigger());

        return true;
    }

    /**
     * Returns an Expression.
     *
     * <ul>
     *     <li>
     *         This can be a StringLiteral:<br>
     *         <pre>
     *         "This is a String Literal"
     *         </pre>
     *     </li>
     *
     *     <li>
     *         a variable reference: <br>
     *         <pre>
     *         $this-is-a-variable-reference
     *         </pre>
     *     </li>
     *
     *     <li>
     *         a term: <br>
     *         <pre>
     *         -this-is-a-term
     *         -and-this-is-a-term-with-arguments($arg = "Hi")
     *          </pre>
     *     </li>
     *
     *     <li>
     *         a MessageReference: <br>
     *         <pre>
     *         this-is-a-message-reference
     *         </pre>
     *     </li>
     *
     *     <li>
     *         or a function: <br>
     *         <pre>
     *         THISISAFUNCTION($input)
     *         </pre>
     *     </li>
     * </ul>
     * <p>
     *     It is checking what version it could be and is
     *     then returning the parsed variant of it in a tree.
     * </p>
     *
     * @return The expression
     */
    public FluentPlaceable getExpression() {
        FluentPlaceable expression;

        switch (getChar()) {
            case '"' -> {
                increment();
                final int start = getPosition();
                while (getChar() != '"') {
                    if (getChar() == '\\' && peek(1) == '"')
                        increment();

                    increment();
                }
                StringSlice string = substring(start, getPosition());
                increment();
                expression = new FluentPlaceable.StringLiteral(string);
            }
            case '$' -> {
                increment();
                final StringSlice varIdentifier = getIdentifier();
                expression = new FluentPlaceable.VariableReference(varIdentifier);
            }
            default -> expression = expressionGetDefault();
        }

        return expression;
    }

    private FluentPlaceable expressionGetDefault() {
        boolean isTerm = false;

        if (getChar() == '-') {
            isTerm = true;

            increment();
        }

        if (Character.isDigit(getChar())) {
            if (isTerm)
                decrement();

            return FluentPlaceable.NumberLiteral.getNumberLiteral(getNumber());
        }

        StringSlice msgIdentifier = getIdentifier();
        // TODO: Create Functions

        FluentPlaceable expression = new FluentPlaceable.MessageReference(msgIdentifier);

        skipWhitespace();

        if (getChar() == '(') {
            increment();

            int start = getPosition();

            int open = 0;

            while (!(getChar() == ')' && open == 0)) {
                if (isBigger()) {
                    throw new FluentParseException(")", "EOF", getPosition());
                }

                if (getChar() == '(') {
                    open++;
                }

                if (getChar() == ')') {
                    open--;
                }

                increment();
            }

            if (!isTerm) {
                expression = new FluentPlaceable.FunctionReference(
                        expression.getContent(),
                        substring(start, getPosition())
                );
            } else {
                expression = new FluentPlaceable.TermReference(
                        expression.getContent(),
                        substring(start, getPosition())
                );
            }

            increment();
        }

        return expression;
    }

    private StringSlice getNumber() {
        char character = getChar();
        final int start = getPosition();

        while(character != '\0' &&
                Character.isDigit(character)
                || character == '.'
                || character == '-'
        ) {
            increment();
            character = getChar();
        }

        return substring(start, getPosition());
    }

    /**
     * Returns an identifier.
     * An Identifer only starts with a character from the range
     * {@code [a-zA-Z]} and can have any character from the range
     * {@code [a-ZA-Z0-9-_]}.
     *
     * @return The identifier as a StringSlice
     */
    public StringSlice getIdentifier() {
        char character = getChar();
        final int start = getPosition();

        if (!Character.isAlphabetic(character)) {
            throw new FluentParseException("character from range [a-zA-Z]", character, getPosition());
        }

        while(character != '\0' &&
                Character.isAlphabetic(character)
                || Character.isDigit(character)
                || character == '-'
                || character == '_') {
            increment();
            character = getChar();
        }

        return substring(start, getPosition());
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
