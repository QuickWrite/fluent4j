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
        List<Pair<Integer, Integer>> textElements = new ArrayList<>();

        textElements.add(getNextText());

        return new ArrayList<>();
    }

    private Pair<Integer, Integer> getNextText() {
        final int start = index;

        while (content.length() > index && getChar() != '{') {
            index++;
        }

        return new ImmutablePair<>(start, index);
    }

    private FluentPlaceable getPlaceable() {
        skipWhitespace();

        switch (getChar()) {
            case '"':
                index++;
                final int start = index;
                while (getChar() != '"') {
                    index++;
                }

                String string = content.substring(start, index - 1);

                skipWhitespace();

                if (getChar() != '}') {
                    throw new FluentParseException('}', getChar(), index);
                }

                index++;

                return new FluentPlaceable.StringLiteral(string);
            case '$':
                // TODO: Create VariableReference

                break;
            case '-':
            default:
                // TODO: Create SelectExpression

                // message reference
                final String identifier = getIdentifier();
                skipWhitespace();

                if (getChar() != '}') {
                    throw new FluentParseException('}', getChar(), index);
                }

                index++;
                return new FluentPlaceable.MessageReference(identifier);
        }

        return null;
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
                "\t\t\tcontent: " + this.content + "\n" +
                "\t\t}";
    }
}
