package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.StringSlice;

/**
 * The TextElement is just storing a text that does
 * nothing at all.
 */
public class FluentTextElement implements FluentPlaceable {
    private StringSlice content;

    public FluentTextElement(final StringSlice content) {
        this.content = content;
    }

    public boolean isEmpty() {
        while (!content.isBigger()) {
            if(content.getChar() != '\n') {
                return false;
            }

            content.increment();
        }

        return true;
    }

    @Override
    public StringSlice getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "FluentTextElement: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
