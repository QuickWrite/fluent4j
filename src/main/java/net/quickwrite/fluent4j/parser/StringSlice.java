package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.ast.FluentPlaceable;

public class StringSlice {
    private final String base;
    private final int start;
    private final int end;

    private int index;

    public StringSlice(String base) {
        this(base, 0, base.length());
    }

    protected StringSlice(String base, int start, int end) {
        this.base = base;
        this.start = start;

        this.end = Math.min(end, base.length());

        this.index = 0;
    }

    public char getChar() {
        if (isBigger())
            return '\0';

        return this.base.charAt(this.start + this.index);
    }

    public boolean isBigger() {
        return index >= end - start;
    }

    public int getPosition() {
        return this.index;
    }

    public void increment() {
        if (isBigger())
            return;

        this.index++;
    }

    public StringSlice substring(int start, int end) {
        return new StringSlice(this.base, start + this.start, this.start + end);
    }

    public int length() {
        return this.end - this.start + 1;
    }

    public boolean skipWhitespace() {
        if(getChar() != ' ' && !(index >= length())) {
            return false;
        }

        do {
            increment();
        } while(getChar() == ' ' && !(index >= length()));

        return true;
    }

    public boolean skipWhitespaceAndNL() {
        if(getChar() != ' ' && getChar() != '\n' || isBigger()) {
            return false;
        }

        do {
            increment();
        } while((getChar() == ' ' || getChar() == '\n') && !isBigger());

        return true;
    }

    public FluentPlaceable getExpression() {
        FluentPlaceable expression;

        switch (getChar()) {
            case '"':
                increment();
                final int start = getPosition();
                while (getChar() != '"') {
                    increment();
                }

                StringSlice string = substring(start, getPosition());
                increment();

                expression = new FluentPlaceable.StringLiteral(string);
                break;
            case '$':
                increment();
                final StringSlice varIdentifier = getIdentifier();

                expression = new FluentPlaceable.VariableReference(varIdentifier);
                break;
            default:
                expression = expressionGetDefault();
        }

        return expression;
    }

    private FluentPlaceable expressionGetDefault() {
        boolean isTerm = false;

        if (getChar() == '-') {
            isTerm = true;

            increment();
        }

        StringSlice msgIdentifier = getIdentifier();
        // TODO: Create Functions

        FluentPlaceable expression = new FluentPlaceable.MessageReference(msgIdentifier);

        skipWhitespace();

        if (getChar() == '(') {
            increment();

            int start = getPosition();

            while (getChar() != ')') {
                increment();
            }

            if (!isTerm) {
                expression = new FluentPlaceable.FunctionReference(
                        expression.getContent(),
                        substring(start, getPosition())
                );
            } else {
                expression = new FluentPlaceable.TermReference(
                        expression.getContent(),
                        substring(start, getPosition())
                );
            }

            increment();
        }

        return expression;
    }

    public StringSlice getIdentifier() {
        char character = getChar();
        final int start = getPosition();

        while(character != '\0' &&
                Character.isAlphabetic(character)
                || Character.isDigit(character)
                || character == '-'
                || character == '_') {
            increment();
            character = getChar();
        }

        return substring(start, getPosition());
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String toString() {
        return this.base.substring(this.start, this.end);
    }
}
