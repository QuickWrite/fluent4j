package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.ast.*;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Parses a String into a FluentResource so that it can be queried.
 * <p>
 *     Instead of the default implementation of Fluent comments are completely ignored
 *     and Junk nodes are not created while still errors in the Fluent file are getting
 *     reported. <br>
 *     This has the added benefit that the generated AST is smaller in Memory and can
 *     be queried faster.
 * </p>
 */
public class FluentParser {
    private final StringSlice input;

    /**
     * Initializes the Parser with the input String as the
     * Ressource.
     *
     * @param input The Fluent Ressource
     */
    public FluentParser(String input) {
        this.input = new StringSlice(input.replace("\r", ""));
    }

    /**
     * Parses the Fluent Resource that is stored inside of the
     * Object as an attribute and returns the generated AST inside
     * of a FluentResource.
     *
     * @return FluentResource
     */
    public FluentResource parse() {
        List<FluentElement> elementList = new ArrayList<>();
        List<Exception> exceptionList = new LinkedList<>();

        while(input.length() >= input.length()) {
            if (input.getChar() == '#') {
                handleComment();
                continue;
            }

            try {
                FluentBase fluentBase = getBase();

                if (fluentBase != null) {
                    elementList.add(fluentBase);
                    continue;
                }
            } catch (FluentParseException exception) {
                exceptionList.add(exception);
            }

            if (!StringSliceUtil.skipWhitespaceAndNL(input)) {
                if (input.getChar() == '\n' || input.getChar() == ' ' || input.getChar() == '\0') {
                    break;
                }
                exceptionList.add(new FluentParseException("whitespace", input.getChar(), input.getAbsolutePosition()));
            }
        }

        return new FluentResource(elementList, exceptionList);
    }

    private FluentBase getBase() {
        if (Character.isAlphabetic(input.getChar())) {
            StringSlice identifier = StringSliceUtil.getIdentifier(input);

            StringSliceUtil.skipWhitespace(input);

            if(input.getChar() != '=') {
                throw new FluentParseException('=', input.getChar(), input.getAbsolutePosition());
            }

            input.increment();

            final Pair<Pair<StringSlice, Integer>, List<FluentAttribute>> pair = getContent();

            // must be a Message
            return new FluentMessage(identifier, pair.getLeft().getLeft(), pair.getRight(), pair.getLeft().getRight());
        }

        if (input.getChar() == '-') {
            input.increment();

            StringSlice identifier = StringSliceUtil.getIdentifier(input);

            StringSliceUtil.skipWhitespace(input);

            if(input.getChar() != '=') {
                throw new FluentParseException('=', input.getChar(), input.getAbsolutePosition());
            }

            input.increment();

            final Pair<Pair<StringSlice, Integer>, List<FluentAttribute>> pair = getContent();

            // must be a Term
            return new FluentTerm(identifier, pair.getLeft().getLeft(), pair.getRight(), pair.getLeft().getRight());
        }

        return null;
    }

    private void handleComment() {
        while(input.getChar() != '\n' && input.getChar() != '\0') {
            input.increment();
        }
    }

    private Pair<Pair<StringSlice, Integer>, List<FluentAttribute>> getContent() {
        List<FluentAttribute> attributes = new ArrayList<>();
        Pair<StringSlice, Integer> content = getMessageContent();

        while (input.getChar() == '.') {
            input.increment();
            StringSlice identifier = StringSliceUtil.getIdentifier(input);
            StringSliceUtil.skipWhitespace(input);

            if (input.getChar() != '=') {
                throw new FluentParseException('=', input.getChar(), input.getAbsolutePosition());
            }

            input.increment();
            StringSliceUtil.skipWhitespace(input);

            Pair<StringSlice, Integer> messageContent = getMessageContent();

            attributes.add(new FluentAttribute(identifier, messageContent.getLeft(), messageContent.getRight()));
        }

        return new ImmutablePair<>(content, attributes);
    }

    private Pair<StringSlice, Integer> getMessageContent() {
        return getMessageContent(input, character -> character == '.');
    }

    public static Pair<StringSlice, Integer> getMessageContent(StringSlice input, BreakChecker breaker) {
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

    public interface BreakChecker {
        boolean isEndCharacter(char character);
    }
}
