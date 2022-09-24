package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.ast.*;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import net.quickwrite.fluent4j.util.bundle.FluentResource;
import net.quickwrite.fluent4j.util.bundle.SimpleFluentResource;
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
    public static FluentResource parse(final String input) {
        final StringSlice content = convertString(input);

        List<FluentElement> elementList = new ArrayList<>();
        List<FluentParseException> exceptionList = new LinkedList<>();

        while (content.length() >= content.length()) {
            if (content.getChar() == '#') {
                handleComment(content);
                continue;
            }

            try {
                FluentBase fluentBase = getBase(content);

                if (fluentBase != null) {
                    elementList.add(fluentBase);
                    continue;
                }
            } catch (FluentParseException exception) {
                exceptionList.add(exception);
                continue;
            }

            int startSkip = content.getPosition();
            StringSliceUtil.skipWhitespaceAndNL(content);

            if (startSkip == content.getPosition()) {
                if (content.getChar() == '\n' || content.getChar() == ' ' || content.getChar() == '\0') {
                    break;
                }
                exceptionList.add(new FluentParseException("an entry start", content.getCharUTF16(), content.getPosition()));

                while (content.getChar() != '\n' && !content.isBigger()) {
                    content.increment();
                }
            }
        }

        content.setIndex(0);

        return new SimpleFluentResource(elementList, exceptionList);
    }

    private static FluentBase getBase(final StringSlice content) {
        boolean isIdentifier = false;

        if (content.getChar() == '-') {
            content.increment();

            isIdentifier = true;
        } else if (!Character.isAlphabetic(content.getChar())) {
            return null;
        }

        StringSlice identifier = StringSliceUtil.getIdentifier(content);

        StringSliceUtil.skipWhitespace(content);

        if (content.getChar() != '=') {
            throw new FluentParseException('=', content.getCharUTF16(), content.getAbsolutePosition());
        }

        content.increment();

        final Pair<Pair<StringSlice, Integer>, List<FluentAttribute>> pair = getContent(content);

        if (!isIdentifier) {
            // must be a Message
            return new FluentMessage(identifier, pair.getLeft().getLeft(), pair.getRight(), pair.getLeft().getRight());
        } else {
            // must be a Term
            return new FluentTerm(identifier, pair.getLeft().getLeft(), pair.getRight(), pair.getLeft().getRight());
        }
    }

    private static void handleComment(final StringSlice content) {
        while (content.getChar() != '\n' && content.getChar() != '\0') {
            content.increment();
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

    private static Pair<StringSlice, Integer> getMessageContent(final StringSlice content) {
        return getMessageContent(content, character -> character == '.');
    }

    public static Pair<StringSlice, Integer> getMessageContent(final StringSlice content, final BreakChecker breaker) {
        StringSliceUtil.skipWhitespace(content);
        final int start = content.getPosition();
        int lastWhitespace = start;
        boolean first = true;
        int leadingWhitespace = Integer.MAX_VALUE;

        do {
            int whitespace = StringSliceUtil.skipWhitespace(content);
            if (!first && breaker.isEndCharacter(content.getChar())) {
                break;
            }

            if (!first && whitespace < leadingWhitespace && whitespace != 0) {
                leadingWhitespace = whitespace;
            }

            first = false;

            while (content.getChar() != '\n' && content.getChar() != '\0') {
                if (content.getChar() == '{') {
                    content.increment();
                    StringSliceUtil.getPlaceable(content);
                }

                if (content.getChar() != ' ') {
                    lastWhitespace = content.getPosition();
                }

                content.increment();
            }
            content.increment();
        } while (content.getChar() == ' ' || content.getChar() == '\n');

        if (leadingWhitespace == Integer.MAX_VALUE) {
            leadingWhitespace = 0;
        }

        return new ImmutablePair<>(content.substring(start, lastWhitespace + 1), leadingWhitespace);
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
