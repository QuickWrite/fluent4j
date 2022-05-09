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
        this.input = input;
    }

    public FluentResource parse() {
        List<FluentElement> elementList = new ArrayList<>();

        System.out.println(input);

        while(input.length() >= index) {
            if (getChar(index) == '#') {
                handleComment();
                continue;
            }

            if (Character.isAlphabetic(getChar(index))) {
                String identifier = getIdentifier();

                skipWhitespace();

                if(getChar(index) != '=') {
                    throw new FluentParseException("=", getChar(index) + "", index);
                }

                index++;

                Pair<List<String>, List<FluentAttribute>> pair = getContent();

                // must be a Message
                elementList.add(new FluentMessage(identifier, pair.getLeft(), pair.getRight()));

                continue;
            }

            if (getChar(index) == '-') {
                index++;

                String identifier = getIdentifier();

                skipWhitespace();

                if(getChar(index) != '=') {
                    throw new FluentParseException("=", getChar(index) + "", index);
                }

                index++;

                Pair<List<String>, List<FluentAttribute>> pair = getContent();

                // must be a Term
                elementList.add(new FluentTerm(identifier, pair.getLeft(), pair.getRight()));

                continue;
            }

            if (!skipWhitespace()) {
                throw new FluentParseException("whitespace", getChar(index) + "", index);
            }
        }

        return new FluentResource(elementList);
    }

    private void handleComment() {
        while(getChar(index) != '\n' && getChar(index) != '\0') {
            index++;
        }
    }

    private char getChar(int index) {
        if (input.length() <= index)
            return '\0';

        return input.charAt(index);
    }

    private boolean skipWhitespace() {
        if(!(Character.isWhitespace(getChar(index)) && getChar(index) != '\0')) {
            return false;
        }

        while(Character.isWhitespace(getChar(index)) && getChar(index) != '\0') {
            index++;
        }

        return true;
    }

    private String getLine() {
        final int start = index;

        while(getChar(index) != '\n' && getChar(index) != '\0') {
            index++;
        }

        return input.substring(start, index);
    }

    private String getIdentifier() {
        char character = getChar(index);
        final int start = index;

        while(character != '\0' &&
                Character.isAlphabetic(character)
                || Character.isDigit(character)
                || character == '-'
                || character == '_') {
            index++;
            character = getChar(index);
        }

        return input.substring(start, index);
    }

    private Pair<List<String>, List<FluentAttribute>> getContent() {
        List<String> contents = new ArrayList<>();
        List<FluentAttribute> attributes = new ArrayList<>();

        do {
            int start = index;

            skipWhitespace();

            if (getChar(index) == '.') {
                break;
            }

            while (getChar(index) != '\0' && getChar(index) != '\n') {
                index++;
            }

            if (index != start)
                contents.add(input.substring(start, index));

            index++;
        } while(Character.isWhitespace(getChar(index)));

        if (contents.size() == 0) {
            throw new FluentParseException("message", getChar(index) + "", index);
        }

        while (getChar(index) == '.') {
            index++;
            String identifier = getIdentifier();

            skipWhitespace();

            if(getChar(index) != '=') {
                throw new FluentParseException("=", getChar(index) + "", index);
            }

            index++;

            List<String> attributeContents = new ArrayList<>();

            do {
                int start = index;

                skipWhitespace();

                if (getChar(index) == '.') {
                    break;
                }

                while (getChar(index) != '\0' && getChar(index) != '\n') {
                    index++;
                }

                if (index != start)
                    attributeContents.add(input.substring(start, index));

                index++;
            } while(Character.isWhitespace(getChar(index)));

            attributes.add(new FluentAttribute(identifier, attributeContents));
        }

        return new ImmutablePair<>(contents, attributes);
    }
}
