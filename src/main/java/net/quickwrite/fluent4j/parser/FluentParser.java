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

        while(input.length() >= index) {
            if (getChar() == '#') {
                handleComment();
                continue;
            }

            if (Character.isAlphabetic(getChar())) {
                String identifier = getIdentifier();

                skipWhitespace();

                if(getChar() != '=') {
                    throw new FluentParseException('=', getChar(), index);
                }

                index++;

                final Pair<String, List<FluentAttribute>> pair = getContent();

                // must be a Message
                elementList.add(new FluentMessage(identifier, pair.getLeft(), pair.getRight()));
                System.out.println(elementList.get(elementList.size() - 1));

                continue;
            }

            if (getChar() == '-') {
                index++;

                String identifier = getIdentifier();

                skipWhitespace();

                if(getChar() != '=') {
                    throw new FluentParseException('=', getChar(), index);
                }

                index++;

                final Pair<String, List<FluentAttribute>> pair = getContent();

                // must be a Term
                elementList.add(new FluentTerm(identifier, pair.getLeft(), pair.getRight()));
                System.out.println(elementList.get(elementList.size() - 1));

                continue;
            }

            if (!skipWhitespaceAndNL()) {
                throw new FluentParseException("whitespace", getChar(), index);
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
        if(getChar() != ' ' && getChar() != '\0') {
            return false;
        }

        do {
            index++;
        } while(getChar() == ' ' && getChar() != '\0');

        return true;
    }

    private boolean skipWhitespaceAndNL() {
        if(getChar() != ' ' && getChar() != '\n' && getChar() != '\0') {
            return false;
        }

        do {
            index++;
        } while((getChar() == ' ' || getChar() == '\n') && getChar() != '\0');

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
        String content = getMessageContent();

        while (getChar() == '.') {
            index++;
            String identifier = getIdentifier();
            skipWhitespace();

            if (getChar() != '=') {
                throw new FluentParseException('=', getChar(), index);
            }

            index++;
            skipWhitespace();

            attributes.add(new FluentAttribute(identifier, getMessageContent()));
        }

        return new ImmutablePair<>(content, attributes);
    }

    private String getMessageContent() {
        skipWhitespace();
        final int start = index;
        int lastWhitespace = start;
        boolean first = true;

        do {
            skipWhitespace();
            if (!first && getChar() == '.') {
                break;
            }

            first = false;

            while (getChar() != '\n' && getChar() != '\0') {
                if (getChar() != ' ') {
                    lastWhitespace = index;
                }
                index++;
            }
            index++;
        } while (getChar() == ' ');

        return input.substring(start, lastWhitespace + 1);
    }
}
