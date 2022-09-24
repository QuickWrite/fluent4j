package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.util.bundle.FluentResource;
import net.quickwrite.fluent4j.util.bundle.SimpleFluentResource;
import net.quickwrite.fluent4j.ast.*;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Parses a String into a FluentResource so that it can be queried.
 * <p>
 * Instead of the default implementation of Fluent comments are completely ignored
 * and Junk nodes are not created while still errors in the Fluent file are getting
 * reported. <br>
 * This has the added benefit that the generated AST is smaller in Memory and can
 * be queried faster.
 */
public abstract class FluentParser {
    /**
     * Parses the Fluent Resource that is stored inside the
     * Object as an attribute and returns the generated AST inside
     * a FluentResource.
     *
     * @return FluentResource
     */
    public static FluentResource parse(final String content) {
        final StringSlice input = convertString(content);

        List<FluentElement> elementList = new ArrayList<>();
        List<FluentParseException> exceptionList = new LinkedList<>();

        while (input.length() >= input.length()) {
            if (input.getChar() == '#') {
                handleComment(input);
                continue;
            }

            try {
                FluentBase fluentBase = getBase(input);

                if (fluentBase != null) {
                    elementList.add(fluentBase);
                    continue;
                }
            } catch (FluentParseException exception) {
                exceptionList.add(exception);
                continue;
            }

            int startSkip = input.getPosition();
            StringSliceUtil.skipWhitespaceAndNL(input);

            if (startSkip == input.getPosition()) {
                if (input.getChar() == '\n' || input.getChar() == ' ' || input.getChar() == '\0') {
                    break;
                }
                exceptionList.add(new FluentParseException("an entry start", input.getCharUTF16(), input.getPosition()));

                while (input.getChar() != '\n' && !input.isBigger()) {
                    input.increment();
                }
            }
        }

        input.setIndex(0);

        return new SimpleFluentResource(elementList, exceptionList);
    }

    private static FluentBase getBase(final StringSlice input) {
        boolean isIdentifier = false;

        if (input.getChar() == '-') {
            input.increment();

            isIdentifier = true;
        } else if (!Character.isAlphabetic(input.getChar())) {
            return null;
        }

        StringSlice identifier = StringSliceUtil.getIdentifier(input);

        StringSliceUtil.skipWhitespace(input);

        if (input.getChar() != '=') {
            throw new FluentParseException('=', input.getCharUTF16(), input.getAbsolutePosition());
        }

        input.increment();

        final Pair<Pair<StringSlice, Integer>, List<FluentAttribute>> pair = getContent(input);

        if (!isIdentifier) {
            // must be a Message
            return new FluentMessage(identifier, pair.getLeft().getLeft(), pair.getRight(), pair.getLeft().getRight());
        } else {
            // must be a Term
            return new FluentTerm(identifier, pair.getLeft().getLeft(), pair.getRight(), pair.getLeft().getRight());
        }
    }

    private static void handleComment(final StringSlice input) {
        while (input.getChar() != '\n' && input.getChar() != '\0') {
            input.increment();
        }
    }

    private static Pair<Pair<StringSlice, Integer>, List<FluentAttribute>> getContent(final StringSlice input) {
        List<FluentAttribute> attributes = new ArrayList<>();
        Pair<StringSlice, Integer> content = getMessageContent(input);

        while (input.getChar() == '.') {
            input.increment();
            StringSlice identifier = StringSliceUtil.getIdentifier(input);
            StringSliceUtil.skipWhitespace(input);

            if (input.getChar() != '=') {
                throw new FluentParseException('=', input.getCharUTF16(), input.getAbsolutePosition());
            }

            input.increment();
            StringSliceUtil.skipWhitespace(input);

            Pair<StringSlice, Integer> messageContent = getMessageContent(input);

            attributes.add(new FluentAttribute(identifier, messageContent.getLeft(), messageContent.getRight()));
        }

        return new ImmutablePair<>(content, attributes);
    }

    private static Pair<StringSlice, Integer> getMessageContent(final StringSlice input) {
        return getMessageContent(input, character -> character == '.');
    }

    public static Pair<StringSlice, Integer> getMessageContent(final StringSlice input, final BreakChecker breaker) {
        StringSliceUtil.skipWhitespace(input);
        final int start = input.getPosition();
        int lastWhitespace = start;
        boolean first = true;
        int leadingWhitespace = Integer.MAX_VALUE;

        do {
            int whitespace = StringSliceUtil.skipWhitespace(input);
            if (!first && whitespace < leadingWhitespace && whitespace != 0) {
                leadingWhitespace = whitespace;
            }

            if (!first && breaker.isEndCharacter(input.getChar())) {
                break;
            }

            first = false;

            while (input.getChar() != '\n' && input.getChar() != '\0') {
                if (input.getChar() == '{') {
                    input.increment();
                    StringSliceUtil.getExpression(input);
                }

                if (input.getChar() != ' ') {
                    lastWhitespace = input.getPosition();
                }

                input.increment();
            }
            input.increment();
        } while (input.getChar() == ' ' || input.getChar() == '\n');

        if (leadingWhitespace == Integer.MAX_VALUE) {
            leadingWhitespace = 0;
        }

        return new ImmutablePair<>(input.substring(start, lastWhitespace + 1), leadingWhitespace);
    }

    private final static Pattern stringConverter;

    static {
        stringConverter = Pattern.compile("(\\r\\n|\\r|\\f)+");
    }

    private static StringSlice convertString(final String input) {
        return new StringSlice(stringConverter.matcher(input).replaceAll("\n"));
    }

    /**
     * A single interface so that the {@link #getMessageContent}
     * method has the ability to adapt the break checker to the
     * circumstances and anonymous functions can be easily used.
     */
    public interface BreakChecker {
        boolean isEndCharacter(char character);
    }
}
