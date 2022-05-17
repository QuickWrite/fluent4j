package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.exception.FluentParseException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class FluentAttribute extends FluentElement {
    protected final String identifier;

    protected final List<FluentElement> fluentElements;
    protected final String content;

    protected int index = 0;

    public FluentAttribute(String identifier, String content) {
        this.identifier = identifier;

        this.content = content;

        this.fluentElements = parse();
    }

    private List<FluentElement> parse() {
        List<FluentElement> elements = new ArrayList<>();

        while (index < content.length()) {
            if (getChar() == '{') {
                elements.add(getPlaceable());
                continue;
            }

            elements.add(getText());
        }

        return elements;
    }

    private FluentTextElement getText() {
        final int start = index;

        while (content.length() > index && getChar() != '{') {
            index++;
        }

        return new FluentTextElement(content.substring(start, index));
    }

    private FluentPlaceable getPlaceable() {
        index++;
        skipWhitespace();

        FluentPlaceable placeable = null;

        boolean canSelect = false;

        char character = getChar();

        switch (getChar()) {
            case '"':
                index++;
                final int start = index;
                while (getChar() != '"') {
                    index++;
                }

                String string = content.substring(start, index);
                index++;

                placeable = new FluentPlaceable.StringLiteral(string);
                break;
            case '$':
                index++;
                final String varIdentifier = getIdentifier();

                canSelect = true;

                placeable = new FluentPlaceable.VariableReference(varIdentifier);
                break;
            case '-':
                canSelect = true;
                index++;
            default:
                // TODO: Create Functions

                // message reference
                final String msgIdentifier = getIdentifier();

                placeable = new FluentPlaceable.MessageReference(msgIdentifier);
        }

        if (canSelect) {
            index++;
            skipWhitespace();
            if (getChar() == '-') {
                index++;
                if (getChar() != '>') {
                    throw new FluentParseException("->", "-" + getChar(), index);
                }

                index++;

                skipWhitespace();

                List<FluentVariant> fluentVariants = new ArrayList<>();

                while (getChar() != '}') {
                    fluentVariants.add(getVariant());
                }

                placeable = new FluentPlaceable.SelectExpression(placeable, fluentVariants);
            }
        }

        skipWhitespace();

        if (getChar() != '}') {
            throw new FluentParseException('}', getChar(), index);
        }

        index++;

        return placeable;
    }

    private FluentVariant getVariant() {
        skipWhitespace();

        int start = index;

        boolean isDefault = false;

        if (getChar() == '*') {
            isDefault = true;
            index++;

            start = index;
        }

        if (getChar() != '[') {
            throw new FluentParseException('[',  getChar(), index);
        }

        skipWhitespace();

        while (getChar() != ']' && getChar() != ' ' && getChar() != '\n') {
            index++;
        }

        final int end = index;

        skipWhitespace();

        if (getChar() != ']') {
            throw new FluentParseException(']',  getChar(), index);
        }

        String identifier = content.substring(start + 1, end);

        index++;

        start = index;

        do {
            skipWhitespace();

            if (getChar() == '[' || getChar() == '*' || getChar() == '}') {
                break;
            }

            while (getChar() != '\0' && getChar() != '\n') {
                index++;
            }

            index++;
        } while(Character.isWhitespace(getChar()));

        return new FluentVariant(new FluentAttribute(identifier, content.substring(start, index - 1)), isDefault);
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

        return content.substring(start, index);
    }

    private char getChar() {
        if (content.length() <= index)
            return '\0';

        return content.charAt(index);
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

    @Override
    public String toString() {
        return "FluentAttribute: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
