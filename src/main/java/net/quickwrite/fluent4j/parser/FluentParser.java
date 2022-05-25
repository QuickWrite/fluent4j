package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.ast.FluentAttribute;
import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.exception.FluentParseException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class FluentParser {
    private final StringSlice input;

    public FluentParser(String input) {
        this.input = new StringSlice(input.replace("\r", ""));
    }

    public FluentResource parse() {
        List<FluentElement> elementList = new ArrayList<>();

        while(input.length() >= input.length()) {
            if (input.getChar() == '#') {
                handleComment();
                continue;
            }

            if (Character.isAlphabetic(input.getChar())) {
                StringSlice identifier = getIdentifier();

                input.skipWhitespace();

                if(input.getChar() != '=') {
                    throw new FluentParseException('=', input.getChar(), input.getPosition());
                }

                input.increment();

                final Pair<StringSlice, List<FluentAttribute>> pair = getContent();

                // must be a Message
                elementList.add(new FluentMessage(identifier, pair.getLeft(), pair.getRight()));
                System.out.println(elementList.get(elementList.size() - 1));

                continue;
            }

            if (input.getChar() == '-') {
                input.increment();

                StringSlice identifier = getIdentifier();

                input.skipWhitespace();

                if(input.getChar() != '=') {
                    throw new FluentParseException('=', input.getChar(), input.getPosition());
                }

                input.increment();

                final Pair<StringSlice, List<FluentAttribute>> pair = getContent();

                // must be a Term
                elementList.add(new FluentTerm(identifier, pair.getLeft(), pair.getRight()));
                System.out.println(elementList.get(elementList.size() - 1));

                continue;
            }

            if (!input.skipWhitespaceAndNL()) {
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

    private StringSlice getIdentifier() {
        char character = input.getChar();
        final int start = input.getPosition();

        while(character != '\0' &&
                Character.isAlphabetic(character)
                || Character.isDigit(character)
                || character == '-'
                || character == '_') {
            input.increment();
            character = input.getChar();
        }

        return input.substring(start, input.getPosition());
    }

    private Pair<StringSlice, List<FluentAttribute>> getContent() {
        List<FluentAttribute> attributes = new ArrayList<>();
        StringSlice content = getMessageContent();

        while (input.getChar() == '.') {
            input.increment();
            StringSlice identifier = getIdentifier();
            input.skipWhitespace();

            if (input.getChar() != '=') {
                throw new FluentParseException('=', input.getChar(), input.getPosition());
            }

            input.increment();
            input.skipWhitespace();

            attributes.add(new FluentAttribute(identifier, getMessageContent()));
        }

        return new ImmutablePair<>(content, attributes);
    }

    private StringSlice getMessageContent() {
        input.skipWhitespace();
        final int start = input.getPosition();
        int lastWhitespace = start;
        boolean first = true;

        do {
            input.skipWhitespace();
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
        } while (input.getChar() == ' ');

        return input.substring(start, lastWhitespace + 1);
    }
}
