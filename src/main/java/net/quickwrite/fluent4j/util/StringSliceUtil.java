package net.quickwrite.fluent4j.util;

import net.quickwrite.fluent4j.ast.placeable.*;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.exception.FluentParseException;

public final class StringSliceUtil {
    private StringSliceUtil() {
    }

    /**
     * Skips whitespace in the StringSlice. <br>
     * <p>
     * (Only includes spaces and does not consider TAB) <br>
     * <p>
     * It is incrementing the index until there is a part of
     * the StringSlice that does have something else than
     * Whitespace.
     *
     * @return If whitespace could be skipped.
     */
    public static int skipWhitespace(StringSlice slice) {
        if (slice.getChar() != ' ' && !(slice.getPosition() >= slice.length())) {
            return 0;
        }

        int start = slice.getPosition();

        do {
            slice.increment();
        } while (slice.getChar() == ' ' && !(slice.getPosition() >= slice.length()));

        return slice.getPosition() - start;
    }

    /**
     * Skips whitespace and newlines in the StringSlice.
     * <p>
     * (Only includes spaces and newlines and does not
     * consider TAB) <br>
     * <p>
     * It is incrementing the index until there is a part of
     * the StringSlice that does have something else than
     * Whitespace or newline.
     *
     * @return If a Newline was skipped.
     */
    public static boolean skipWhitespaceAndNL(StringSlice slice) {
        if (!(slice.getChar() == ' ' || slice.getChar() == '\n') || slice.isBigger()) {
            return false;
        }

        boolean andNL = false;
        do {
            if (slice.getChar() == '\n') {
                andNL = true;
            }

            slice.increment();
        } while (slice.getChar() == ' ' || slice.getChar() == '\n' && !slice.isBigger());

        return andNL;
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
    public static FluentPlaceable getExpression(StringSlice slice) {
        FluentPlaceable expression;

        switch (slice.getChar()) {
            case '"' -> {
                slice.increment();
                final int start = slice.getPosition();
                while (!(slice.getChar() == '"' || slice.getChar() == '\n') && !slice.isBigger()) {
                    if (slice.getChar() == '\\' && slice.peek(1) == '"')
                        slice.increment();

                    slice.increment();
                }

                if (slice.getChar() != '"') {
                    throw new FluentParseException("String expression terminator '\"'", slice.getChar(), slice.getPosition() + 1);
                }

                StringSlice string = slice.substring(start, slice.getPosition());
                slice.increment();
                expression = new StringLiteral(string);
            }
            case '$' -> {
                slice.increment();
                final StringSlice varIdentifier = getIdentifier(slice);
                expression = new VariableReference(varIdentifier);
            }
            default -> expression = expressionGetDefault(slice);
        }

        return expression;
    }

    private static FluentPlaceable expressionGetDefault(StringSlice slice) {
        boolean isTerm = false;

        if (slice.getChar() == '-') {
            isTerm = true;

            slice.increment();
        }

        if (Character.isDigit(slice.getChar())) {
            if (isTerm)
                slice.decrement();

            return NumberLiteral.getNumberLiteral(getNumber(slice));
        }

        StringSlice msgIdentifier = getIdentifier(slice);

        FluentPlaceable expression;
        if (isTerm) {
            expression = new TermReference(msgIdentifier);
        } else {
            expression = new MessageReference(msgIdentifier);
        }

        skipWhitespace(slice);

        if (slice.getChar() == '(') {
            slice.increment();

            int start = slice.getPosition();

            int open = 0;

            while (!(slice.getChar() == ')' && open == 0)) {
                if (slice.isBigger()) {
                    throw new FluentParseException(")", "EOF", slice.getAbsolutePosition());
                }

                if (slice.getChar() == '(') {
                    open++;
                }

                if (slice.getChar() == ')') {
                    open--;
                }

                slice.increment();
            }

            if (!isTerm) {
                expression = new FunctionReference(
                        expression.getContent(),
                        slice.substring(start, slice.getPosition())
                );
            } else {
                expression = new TermReference(
                        expression.getContent(),
                        slice.substring(start, slice.getPosition())
                );
            }

            slice.increment();
        }

        return expression;
    }

    private static StringSlice getNumber(StringSlice slice) {
        char character = slice.getChar();
        final int start = slice.getPosition();

        while (character != '\0' &&
                Character.isDigit(character)
                || character == '.'
                || character == '-'
        ) {
            slice.increment();
            character = slice.getChar();
        }

        return slice.substring(start, slice.getPosition());
    }

    /**
     * Returns an identifier.
     * An Identifer only starts with a character from the range
     * {@code [a-zA-Z]} and can have any character from the range
     * {@code [a-ZA-Z0-9-_]}.
     *
     * @return The identifier as a StringSlice
     */
    public static StringSlice getIdentifier(StringSlice slice) {
        char character = slice.getChar();
        final int start = slice.getPosition();

        if (!Character.isAlphabetic(character)) {
            throw new FluentParseException("character from range [a-zA-Z]", character, slice.getAbsolutePosition());
        }

        while (character != '\0' &&
                Character.isAlphabetic(character)
                || Character.isDigit(character)
                || character == '-'
                || character == '_') {
            slice.increment();
            character = slice.getChar();
        }

        return slice.substring(start, slice.getPosition());
    }
}
