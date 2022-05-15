package net.quickwrite.fluent4j.ast;

public class FluentVariant extends FluentElement {
    private final String identifier;
    private final FluentAttribute content;

    public FluentVariant(FluentAttribute content) {
        this.identifier = content.identifier;
        this.content = content;
    }

    @Override
    public String toString() {
        return "FluentVariant: {\n" +
                "\t\t\tidentifier: " + this.identifier + "\n" +
                "\t\t\tcontent: " + this.content + "\n" +
                "\t\t}";
    }
}
