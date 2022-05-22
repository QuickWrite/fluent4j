package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.exception.FluentSelectException;

import java.util.ArrayList;
import java.util.List;

public class FluentAttribute extends FluentElement {
    protected final String identifier;

    protected final List<FluentElement> fluentElements;
    protected String content;

    protected int index = 0;

    public FluentAttribute(String identifier, String content) {
        this.identifier = identifier;

        this.content = content;

        this.fluentElements = parse();

        this.content = null;
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

        FluentPlaceable placeable;

        boolean canSelect = false;
        boolean canFunction = false;

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
                String msgIdentifier = getIdentifier();

                placeable = new FluentPlaceable.MessageReference(msgIdentifier);
                break;
            default:
                // TODO: Create Functions

                // message reference
                msgIdentifier = getIdentifier();

                canFunction = true;

                placeable = new FluentPlaceable.MessageReference(msgIdentifier);
        }

        skipWhitespace();

        if (canFunction && getChar() == '(') {
            index++;

            int start = index;

            while (getChar() != ')') {
                index++;
            }

            placeable = new FluentPlaceable.FluentFunctionReference(
                    placeable.getContent(),
                    content.substring(start, index)
            );

            index++;

            canSelect = true;
        }

        skipWhitespace();

        if (canSelect && getChar() == '-') {
            index++;
            if (getChar() != '>') {
                throw new FluentParseException("->", "-" + getChar(), index);
            }

            index++;

            skipWhitespaceAndNL();

            List<FluentVariant> fluentVariants = new ArrayList<>();

            while (getChar() != '}') {
                fluentVariants.add(getVariant());
            }

            int defaults = 0;
            for (FluentVariant variant : fluentVariants) {
                if (variant.isDefault())
                    defaults++;
            }

            if (defaults == 0) {
                throw new FluentSelectException("Expected one of the variants to be marked as default (*)");
            }
            if (defaults > 1) {
                throw new FluentSelectException("Only one variant can be marked as default (*)");
            }

            placeable = new FluentPlaceable.SelectExpression(placeable, fluentVariants);
        }

        skipWhitespaceAndNL();

        if (getChar() != '}') {
            throw new FluentParseException('}', getChar(), index);
        }

        index++;

        return placeable;
    }

    private FluentVariant getVariant() {
        skipWhitespaceAndNL();

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
        int lastWhitespace = start;

        do {
            skipWhitespace();

            if (getChar() == '[' || getChar() == '*' || getChar() == '}') {
                break;
            }

            while (getChar() != '\0' && getChar() != '\n') {
                if (getChar() != ' ') {
                    lastWhitespace = index;
                }
                index++;
            }

            index++;
        } while(getChar() == ' ');

        return new FluentVariant(new FluentAttribute(identifier, content.substring(start, lastWhitespace + 1)), isDefault);
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
        if(getChar() != ' ' && getChar() != '\0') {
            return false;
        }

        while(getChar() == ' ' && getChar() != '\0') {
            index++;
        }

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

    @Override
    public String toString() {
        return "FluentAttribute: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
