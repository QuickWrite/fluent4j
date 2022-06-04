package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.ast.FluentAttribute;
import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
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

        while(input.length() >= input.length()) {
            if (input.getChar() == '#') {
                handleComment();
                continue;
            }

            if (Character.isAlphabetic(input.getChar())) {
                StringSlice identifier = StringSliceUtil.getIdentifier(input);

                StringSliceUtil.skipWhitespace(input);

                if(input.getChar() != '=') {
                    throw new FluentParseException('=', input.getChar(), input.getPosition());
                }

                input.increment();

                final Pair<StringSlice, List<FluentAttribute>> pair = getContent();

                // must be a Message
                elementList.add(new FluentMessage(identifier, pair.getLeft(), pair.getRight()));

                continue;
            }

            if (input.getChar() == '-') {
                input.increment();

                StringSlice identifier = StringSliceUtil.getIdentifier(input);

                StringSliceUtil.skipWhitespace(input);

                if(input.getChar() != '=') {
                    throw new FluentParseException('=', input.getChar(), input.getPosition());
                }

                input.increment();

                final Pair<StringSlice, List<FluentAttribute>> pair = getContent();

                // must be a Term
                elementList.add(new FluentTerm(identifier, pair.getLeft(), pair.getRight()));

                continue;
            }

            if (!StringSliceUtil.skipWhitespaceAndNL(input)) {
                if (input.getChar() == '\n' || input.getChar() == ' ' || input.getChar() == '\0') {
                    break;
                }
                throw new FluentParseException("whitespace", input.getChar(), input.getPosition());
            }
        }

        return new FluentResource(elementList);
    }

    private void handleComment() {
        while(input.getChar() != '\n' && input.getChar() != '\0') {
            input.increment();
        }
    }

    private Pair<StringSlice, List<FluentAttribute>> getContent() {
        List<FluentAttribute> attributes = new ArrayList<>();
        StringSlice content = getMessageContent();

        while (input.getChar() == '.') {
            input.increment();
            StringSlice identifier = StringSliceUtil.getIdentifier(input);
            StringSliceUtil.skipWhitespace(input);

            if (input.getChar() != '=') {
                throw new FluentParseException('=', input.getChar(), input.getPosition());
            }

            input.increment();
            StringSliceUtil.skipWhitespace(input);

            attributes.add(new FluentAttribute(identifier, getMessageContent()));
        }

        return new ImmutablePair<>(content, attributes);
    }

    private StringSlice getMessageContent() {
        StringSliceUtil.skipWhitespace(input);
        final int start = input.getPosition();
        int lastWhitespace = start;
        boolean first = true;

        do {
            StringSliceUtil.skipWhitespace(input);
            if (!first && input.getChar() == '.') {
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

        return input.substring(start, lastWhitespace + 1);
    }
}
