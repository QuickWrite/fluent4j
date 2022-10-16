package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

/**
 * The TextElement is just storing a text that does
 * nothing at all.
 */
public class FluentTextElement implements FluentElement {
    private final String text;

    public FluentTextElement(final StringSlice content, final int whitespace) {
        this.text = getText(content, whitespace);
    }

    private String getLine(StringSlice content, int whitespace) {
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

    private String getText(final StringSlice content, final int whitespace) {
        StringBuilder text = new StringBuilder();
        boolean first = true;

        while (!content.isBigger()) {
            text.append(getLine(content, first ? 0 : whitespace));

            first = false;
            content.increment();
        }

        if(text.charAt(text.length() - 1) == '\n') {
            text.deleteCharAt(text.length() - 1);
        }

        return text.toString();
    }

    public boolean isEmpty() {
        return text.isEmpty();
    }

    @Override
    public boolean matches(final DirectFluentBundle bundle, final FluentElement selector) {
        return selector.stringValue().equals(this.text);
    }

    @Override
    public String stringValue() {
        return this.text;
    }

    @Override
    public CharSequence getResult(AccessorBundle bundle) {
        return this.text;
    }

    @Override
    public String toString() {
        return "FluentTextElement: {\n" +
                "\t\t\ttext: \"" + this.text + "\"\n" +
                "\t\t}";
    }
}
