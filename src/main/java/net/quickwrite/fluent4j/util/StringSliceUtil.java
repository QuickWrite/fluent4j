package net.quickwrite.fluent4j.util;

import net.quickwrite.fluent4j.ast.FluentAttribute;
import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentVariant;
import net.quickwrite.fluent4j.ast.placeable.*;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.exception.FluentSelectException;
import net.quickwrite.fluent4j.parser.FluentParser;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FunctionFluentArgs;
import net.quickwrite.fluent4j.util.args.FunctionFluentArguments;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of different utility methods
 * for the {@link StringSlice}.
 */
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
    public static int skipWhitespace(final StringSlice slice) {
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
    public static boolean skipWhitespaceAndNL(final StringSlice slice) {
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
    public static FluentPlaceable getExpression(final StringSlice slice) {
        FluentPlaceable expression;

        switch (slice.getChar()) {
            case '"' -> {
                slice.increment();
                final int start = slice.getPosition();
                skipStringLiteral(slice);

                if (slice.getChar() != '"') {
                    slice.decrement(2);
                    throw new FluentParseException("String expression terminator '\"'", slice.getCharUTF16(), slice.getPosition() + 1);
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

    public static void skipStringLiteral(final StringSlice slice) {
        while (!(slice.getChar() == '"' || slice.getChar() == '\n') && !slice.isBigger()) {
            final char peek = slice.peek(1);
            if (slice.getChar() == '\\' && (peek == '"' || peek == '\\'))
                slice.increment();

            slice.increment();
        }
    }

    private static FluentPlaceable expressionGetDefault(final StringSlice slice) {
        boolean isTerm = false;

        if (slice.getChar() == '-') {
            isTerm = true;

            slice.increment();
        }

        if (Character.isDigit(slice.getChar())) {
            if (isTerm)
                slice.decrement();

            final StringSlice number = getNumber(slice);
            try {
                return NumberLiteral.getNumberLiteral(number);
            } catch (final NumberFormatException ignored) {
                throw new FluentParseException("Number", number.toString(), number.getPosition() - number.length());
            }
        }

        final StringSlice msgIdentifier = getIdentifier(slice);

        FluentPlaceable expression = (isTerm) ? new TermReference(msgIdentifier) : new MessageReference(msgIdentifier);

        skipWhitespaceAndNL(slice);

        return switch (slice.getChar()) {
            case '(' -> expressionGetFunction(slice, expression, isTerm);
            case '.' -> expressionGetAttribute(slice, expression, isTerm);
            default -> expression;
        };

    }

    private static FluentPlaceable expressionGetFunction(final StringSlice slice, FluentPlaceable expression, final boolean isTerm) {
        slice.increment();

        expression = (!isTerm) ?
                new FunctionReference(
                        expression.stringValue(),
                        parseArguments(slice)
                ) :
                new TermReference(
                        expression.stringValue(),
                        parseArguments(slice)
                );

        slice.increment();

        return expression;
    }

    private static FluentPlaceable expressionGetAttribute(final StringSlice slice, FluentPlaceable expression, final boolean isTerm) {
        slice.increment();
        final StringSlice identifier = StringSliceUtil.getIdentifier(slice);

        if (isTerm) {
            skipWhitespaceAndNL(slice);
            FluentArgs arguments = null;

            if (slice.getChar() == '(') {
                slice.increment();
                arguments = parseArguments(slice);
                slice.increment();
            }
            expression = new AttributeReference.TermAttributeReference(expression, identifier, arguments);
        } else {
            expression = new AttributeReference(expression, identifier);
        }

        return expression;
    }

    /**
     * Returns a simple {@link FluentArgs} object that
     * contains the arguments that can be created in the
     * {@code .ftl}-files.
     *
     * <p>
     * The arguments are always in a format
     * of being in {@code ()} and being separated
     * by {@code ,}.
     * </p>
     * <p>
     * There are two different types of arguments:
     *     <ul>
     *         <li>
     *             Positional Arguments:
     *             <br />
     *             Arguments that don't have a name and
     *             are just denominated by their position in the
     *             arguments.
     *         </li>
     *         <li>
     *             Named Arguments:
     *             <br />
     *             Arguments that have a name that is being
     *             declared in front of the argument by
     *             having a {@code :} as a separator.
     *             <br />
     *             This means that to create a named argument
     *             the argument is created by having this format:
     *             <code>{{name}} : {{argument}}</code>
     *             <br />
     *             They can only be accessed by their name as
     *             the key.
     *         </li>
     *     </ul>
     * </p>
     * <p>
     *     If there are no arguments in the brackets
     *     the function will return {@link FluentArgs#EMPTY_ARGS}.
     * </p>
     *
     * @param content The content the arguments are in
     * @return The arguments as an option
     */
    private static FluentArgs parseArguments(final StringSlice content) {
        if (content == null) {
            return FluentArgs.EMPTY_ARGS;
        }

        StringSliceUtil.skipWhitespaceAndNL(content);
        if (content.isBigger()) {
            return FunctionFluentArgs.EMPTY_ARGS;
        }

        final FunctionFluentArgs arguments = new FunctionFluentArguments();

        while (!content.isBigger() && content.getChar() != ')') {
            Pair<String, FluentElement> argument = getArgument(content);

            if (argument.getLeft() != null) {
                arguments.setNamed(argument.getLeft(), argument.getRight());
            } else {
                arguments.addPositional(argument.getRight());
            }

            StringSliceUtil.skipWhitespaceAndNL(content);

            if (content.getChar() != ',') {
                break;
            }
            content.increment();

            StringSliceUtil.skipWhitespaceAndNL(content);
        }

        return arguments;
    }

    private static Pair<String, FluentElement> getArgument(final StringSlice content) {
        FluentPlaceable placeable = StringSliceUtil.getExpression(content);
        String identifier = null;

        StringSliceUtil.skipWhitespaceAndNL(content);

        if (content.getChar() == ':') {
            content.increment();
            StringSliceUtil.skipWhitespaceAndNL(content);

            identifier = placeable.stringValue();

            placeable = StringSliceUtil.getExpression(content);
        }

        return new ImmutablePair<>(identifier, placeable);
    }

    private static StringSlice getNumber(final StringSlice slice) {
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
     * An identifier only starts with a character from the range
     * {@code [a-zA-Z]} and can have any character from the range
     * {@code [a-ZA-Z0-9-_]}.
     *
     * @return The identifier as a StringSlice
     */
    public static StringSlice getIdentifier(final StringSlice slice) {
        char character = slice.getChar();
        final int start = slice.getPosition();

        if (!Character.isAlphabetic(character)) {
            throw new FluentParseException("character from range [a-zA-Z]", slice.getCharUTF16(), slice.getAbsolutePosition());
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

    /**
     * Returns the parsed version of the {@link FluentPlaceable}
     * that is inside the <code>{}</code>.
     *
     * <p>
     * This can either be a simple expression or
     * a complete {@link SelectExpression}.
     * </p>
     *
     * @param content The content that the placeable currently is at
     * @return The Placeable
     */
    public static FluentPlaceable getPlaceable(final StringSlice content) {
        StringSliceUtil.skipWhitespaceAndNL(content);

        FluentPlaceable placeable = StringSliceUtil.getExpression(content);

        boolean canSelect = placeable instanceof FluentSelectable;

        StringSliceUtil.skipWhitespaceAndNL(content);

        if (canSelect && content.getChar() == '-') {
            content.increment();
            if (content.getChar() != '>') {
                throw new FluentParseException("->", "-" + content.getCharUTF16(), content.getAbsolutePosition());
            }

            content.increment();

            StringSliceUtil.skipWhitespaceAndNL(content);

            final List<FluentVariant> fluentVariants = new ArrayList<>();
            FluentVariant defaultVariant = null;

            while (content.getChar() != '}') {
                final Pair<FluentVariant, Boolean> variant = getVariant(content);

                fluentVariants.add(variant.getLeft());

                if (!variant.getRight()) {
                    continue;
                }

                if (defaultVariant != null) {
                    throw new FluentSelectException("Only one variant can be marked as default (*)");
                }

                defaultVariant = variant.getLeft();
            }

            if (defaultVariant == null) {
                throw new FluentSelectException("Expected one of the variants to be marked as default (*)");
            }

            placeable = new SelectExpression(placeable, fluentVariants, defaultVariant);
        }

        StringSliceUtil.skipWhitespaceAndNL(content);

        return placeable;
    }

    private static Pair<FluentVariant, Boolean> getVariant(final StringSlice content) {
        StringSliceUtil.skipWhitespaceAndNL(content);

        boolean isDefault = false;

        if (content.getChar() == '*') {
            isDefault = true;
            content.increment();
        }

        if (content.getChar() != '[') {
            throw new FluentParseException('[', content.getCharUTF16(), content.getAbsolutePosition());
        }

        content.increment();

        StringSliceUtil.skipWhitespace(content);

        final StringSlice identifier = getVariantIdentifier(content);

        StringSliceUtil.skipWhitespace(content);

        if (content.getChar() != ']') {
            throw getVariantException(content, identifier.toString(), "]");
        }

        content.increment();

        final Pair<StringSlice, Integer> stringSliceContent = FluentParser.getMessageContent(content,
                character -> character == '[' || character == '*' || character == '}');

        final FluentAttribute attribute = new FluentAttribute(
                identifier,
                stringSliceContent.getLeft(),
                stringSliceContent.getRight()
        );

        return new ImmutablePair<>(new FluentVariant(attribute), isDefault);
    }

    private static StringSlice getVariantIdentifier(final StringSlice content) {
        char character = content.getChar();
        final int start = content.getPosition();

        while (character != ' '
                && character != '\n'
                && character != ']'
                && character != '\0'
        ) {
            content.increment();
            character = content.getChar();
        }

        return content.substring(start, content.getPosition());
    }

    private static FluentParseException getVariantException(final StringSlice content, final String prev, final String expected) {
        int start = content.getPosition();

        while (content.getChar() != ']' && !content.isBigger()) {
            content.increment();
        }

        return new FluentParseException(expected,
                prev + content.substring(start, content.getPosition()).toString(),
                content.getAbsolutePosition());
    }
}
