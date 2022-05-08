package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.exception.FluentParseException;

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

                // must be a Message
                elementList.add(new FluentMessage(identifier, getContent()));
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

                // must be a Term
                elementList.add(new FluentTerm(identifier, getContent()));

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

    private String getContent() {
        final int start = index;

        do {
            while (getChar(index) != '\0' && getChar(index) != '\n') {
                index++;
            }
            index++;
        } while(Character.isWhitespace(getChar(index)));

        return input.substring(start, index - 1);
    }
}
