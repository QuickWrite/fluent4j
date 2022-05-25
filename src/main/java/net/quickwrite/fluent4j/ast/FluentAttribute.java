package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.exception.FluentSelectException;
import net.quickwrite.fluent4j.parser.StringSlice;

import java.util.ArrayList;
import java.util.List;

public class FluentAttribute extends FluentElement {
    protected final StringSlice identifier;

    protected final List<FluentElement> fluentElements;
    protected StringSlice content;

    public FluentAttribute(StringSlice identifier, StringSlice content) {
        this.identifier = identifier;

        this.content = content;

        this.fluentElements = parse();
    }

    private List<FluentElement> parse() {
        List<FluentElement> elements = new ArrayList<>();

        while (!content.isBigger()) {
            if (content.getChar() == '{' && content.getChar() != '\0') {
                elements.add(getPlaceable());
                continue;
            }

            FluentTextElement text = getText();

            if (text.isEmpty()) {
                continue;
            }

            elements.add(text);
        }

        return elements;
    }

    private FluentTextElement getText() {
        final int start = content.getPosition();

        while (!content.isBigger() && content.getChar() != '{') {
            content.increment();
        }

        return new FluentTextElement(content.substring(start, content.getPosition()));
    }

    private FluentPlaceable getPlaceable() {
        content.increment();
        content.skipWhitespace();

        FluentPlaceable placeable;

        boolean canSelect = false;
        boolean canFunction = false;

        switch (content.getChar()) {
            case '"':
                content.increment();
                final int start = content.getPosition();
                while (content.getChar() != '"') {
                    content.increment();
                }

                StringSlice string = content.substring(start, content.getPosition());
                content.increment();

                placeable = new FluentPlaceable.StringLiteral(string);
                break;
            case '$':
                content.increment();
                final StringSlice varIdentifier = getIdentifier();

                canSelect = true;

                placeable = new FluentPlaceable.VariableReference(varIdentifier);
                break;
            case '-':
                canSelect = true;
                content.increment();
                StringSlice msgIdentifier = getIdentifier();

                placeable = new FluentPlaceable.MessageReference(msgIdentifier);
                break;
            default:
                // TODO: Create Functions

                // message reference
                msgIdentifier = getIdentifier();

                canFunction = true;

                placeable = new FluentPlaceable.MessageReference(msgIdentifier);
        }

        content.skipWhitespace();

        if (canFunction && content.getChar() == '(') {
            content.increment();

            int start = content.getPosition();

            while (content.getChar() != ')') {
                content.increment();
            }

            placeable = new FluentPlaceable.FunctionReference(
                    placeable.getContent(),
                    content.substring(start, content.getPosition())
            );

            content.increment();

            canSelect = true;
        }

        content.skipWhitespace();

        if (canSelect && content.getChar() == '-') {
            content.increment();
            if (content.getChar() != '>') {
                throw new FluentParseException("->", "-" + content.getChar(), content.getPosition());
            }

            content.increment();

            content.skipWhitespaceAndNL();

            List<FluentVariant> fluentVariants = new ArrayList<>();

            while (content.getChar() != '}') {
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

        content.skipWhitespaceAndNL();

        if (content.getChar() != '}') {
            throw new FluentParseException('}', content.getChar(), content.getPosition());
        }

        content.increment();

        return placeable;
    }

    private FluentVariant getVariant() {
        content.skipWhitespaceAndNL();

        int start = content.getPosition();

        boolean isDefault = false;

        if (content.getChar() == '*') {
            isDefault = true;
            content.increment();

            start = content.getPosition();
        }

        if (content.getChar() != '[') {
            throw new FluentParseException('[',  content.getChar(), content.getPosition());
        }

        content.skipWhitespace();

        while (content.getChar() != ']' && content.getChar() != ' ' && content.getChar() != '\n') {
            content.increment();
        }

        final int end = content.getPosition();

        content.skipWhitespace();

        if (content.getChar() != ']') {
            throw new FluentParseException(']',  content.getChar(), content.getPosition());
        }

        StringSlice identifier = content.substring(start + 1, end);

        content.increment();

        start = content.getPosition();
        int lastWhitespace = start;

        do {
            content.skipWhitespace();

            if (content.getChar() == '[' || content.getChar() == '*' || content.getChar() == '}') {
                break;
            }

            while (content.getChar() != '\0' && content.getChar() != '\n') {
                if (content.getChar() != ' ') {
                    lastWhitespace = content.getPosition();
                }
                content.increment();
            }

            content.increment();
        } while(content.getChar() == ' ');

        return new FluentVariant(new FluentAttribute(identifier, content.substring(start, lastWhitespace + 1)), isDefault);
    }

    private StringSlice getIdentifier() {
        char character = content.getChar();
        final int start = content.getPosition();

        while(character != '\0' &&
                Character.isAlphabetic(character)
                || Character.isDigit(character)
                || character == '-'
                || character == '_') {
            content.increment();
            character = content.getChar();
        }

        return content.substring(start, content.getPosition());
    }

    @Override
    public String toString() {
        return "FluentAttribute: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
