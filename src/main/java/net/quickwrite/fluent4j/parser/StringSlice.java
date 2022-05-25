package net.quickwrite.fluent4j.parser;

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

        if (end >= base.length())
            this.end = base.length();
        else
            this.end = end;

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

    public String toString() {
        return this.base.substring(this.start, this.end);
    }
}
