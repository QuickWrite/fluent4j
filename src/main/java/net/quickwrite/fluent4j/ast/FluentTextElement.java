package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

/**
 * The TextElement is just storing a text that does
 * nothing at all.
 */
public class FluentTextElement implements FluentElement, FluentArgument<String> {
    private final StringSlice content;
    private final String text;

    public FluentTextElement(final StringSlice content, final int whitespace) {
        this.content = content;
        this.text = getText(whitespace);
    }

    public boolean isEmpty() {
        while (!content.isBigger()) {
            if(!(content.getChar() == '\n' || content.getChar() == ' ')) {
                return false;
            }

            content.increment();
        }

        content.setIndex(0);

        return true;
    }

    private String getLine(int whitespace) {
        if (content.getChar() == '\n') {
            return "\n";
        }

        int start = 0;
        while (start < whitespace && !content.isBigger()) {
            if (content.getChar() == '\n') {
                return "\n";
            }

            start++;
            content.increment();
        }

        boolean onlyWhitespace = true;

        start = content.getPosition();
        while(content.getChar() != '\n' && !content.isBigger()) {
            if(content.getChar() != ' ' || whitespace == 0) {
                onlyWhitespace = false;
            }
            content.increment();
        }

        if (onlyWhitespace) {
            return "\n";
        }

        char peek = content.peek(1);

        return content.substring(start, content.getPosition() + (peek == '{' || peek == '\0' ? 0 : 1)).toString();
    }

    private String getText(int whitespace) {
        StringBuilder text = new StringBuilder();
        boolean first = true;

        while (!content.isBigger()) {
            text.append(getLine(first ? 0 : whitespace));

            first = false;
            content.increment();
        }

        if(text.charAt(text.length() - 1) == '\n') {
            text.deleteCharAt(text.length() - 1);
        }

        return text.toString();
    }

    @Override
    public String valueOf() {
        return this.text;
    }

    @Override
    public boolean matches(FluentArgument<?> selector) {
        return selector.stringValue().equals(this.text);
    }

    @Override
    public String stringValue() {
        return this.text;
    }

    @Override
    public String getResult(FluentBundle bundle, FluentArgs arguments) {
        return this.text;
    }

    @Override
    public String toString() {
        return "FluentTextElement: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\ttext: \"" + this.text + "\"\n" +
                "\t\t}";
    }
}
