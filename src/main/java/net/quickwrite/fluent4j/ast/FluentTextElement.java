package net.quickwrite.fluent4j.ast;

public class FluentTextElement extends FluentElement {
    private String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FluentTextElement: {\n" +
                "\t\t\tcontent: " + this.content + "\n" +
                "\t\t}";
    }
}
