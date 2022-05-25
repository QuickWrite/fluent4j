package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.parser.StringSlice;

public class FluentTextElement extends FluentElement {
    private StringSlice content;

    public FluentTextElement(final StringSlice content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FluentTextElement: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
