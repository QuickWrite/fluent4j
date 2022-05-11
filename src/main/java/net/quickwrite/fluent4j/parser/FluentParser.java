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
    private final String input;
    private int index = 0;

    public FluentParser(String input) {
        this.input = input.replace("\r", "");
    }

    public FluentResource parse() {
        List<FluentElement> elementList = new ArrayList<>();

        System.out.println(input);

        while(input.length() >= index) {
            if (getChar() == '#') {
                handleComment();
                continue;
            }

            if (Character.isAlphabetic(getChar())) {
                String identifier = getIdentifier();

                skipWhitespace();

                if(getChar() != '=') {
                    throw new FluentParseException("=", getChar() + "", index);
                }

                index++;

                final Pair<String, List<FluentAttribute>> pair = getContent();

                // must be a Message
                elementList.add(new FluentMessage(identifier, pair.getLeft(), pair.getRight()));

                continue;
            }

            if (getChar() == '-') {
                index++;

                String identifier = getIdentifier();

                skipWhitespace();

                if(getChar() != '=') {
                    throw new FluentParseException("=", getChar() + "", index);
                }

                index++;

                final Pair<String, List<FluentAttribute>> pair = getContent();

                // must be a Term
                elementList.add(new FluentTerm(identifier, pair.getLeft(), pair.getRight()));

                continue;
            }

            if (!skipWhitespace()) {
                throw new FluentParseException("whitespace", getChar() + "", index);
            }
        }

        return new FluentResource(elementList);
    }

    private void handleComment() {
        while(getChar() != '\n' && getChar() != '\0') {
            index++;
        }
    }

    private char getChar() {
        if (input.length() <= index)
            return '\0';

        return input.charAt(index);
    }

    private boolean skipWhitespace() {
        if(!(Character.isWhitespace(getChar()) && getChar() != '\0')) {
            return false;
        }

        while(Character.isWhitespace(getChar()) && getChar() != '\0') {
            index++;
        }

        return true;
    }

    private String getLine() {
        final int start = index;

        while(getChar() != '\n' && getChar() != '\0') {
            index++;
        }

        return input.substring(start, index);
    }

    private String getIdentifier() {
        char character = getChar();
        final int start = index;

        while(character != '\0' &&
                Character.isAlphabetic(character)
                || Character.isDigit(character)
                || character == '-'
                || character == '_') {
            index++;
            character = getChar();
        }

        return input.substring(start, index);
    }

    private Pair<String, List<FluentAttribute>> getContent() {
        List<FluentAttribute> attributes = new ArrayList<>();
        final int start_g = index;

        do {
            skipWhitespace();

            if (getChar() == '.') {
                break;
            }

            while (getChar() != '\0' && getChar() != '\n') {
                index++;
            }

            index++;
        } while(Character.isWhitespace(getChar()));

        final String content = input.substring(start_g, index - 1);

        while (getChar() == '.') {
            index++;
            String identifier = getIdentifier();

            skipWhitespace();

            if(getChar() != '=') {
                throw new FluentParseException("=", getChar() + "", index);
            }

            index++;

            final int start = index;

            do {
                skipWhitespace();

                if (getChar() == '.') {
                    break;
                }

                while (getChar() != '\0' && getChar() != '\n') {
                    index++;
                }

                index++;
            } while(Character.isWhitespace(getChar()));

            attributes.add(new FluentAttribute(identifier, input.substring(start, index - 1)));
        }

        return new ImmutablePair<>(content, attributes);
    }
}
