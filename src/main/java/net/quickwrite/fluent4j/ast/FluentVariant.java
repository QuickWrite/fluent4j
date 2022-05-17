package net.quickwrite.fluent4j.ast;

public class FluentVariant extends FluentElement {
    private final String identifier;
    private final FluentAttribute content;
    private final boolean defaultVariant;

    public FluentVariant(FluentAttribute content, boolean defaultVariant) {
        this.identifier = content.identifier;
        this.content = content;
        this.defaultVariant = defaultVariant;
    }

    public boolean isDefault() {
        return this.defaultVariant;
    }

    @Override
    public String toString() {
        return "FluentVariant: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\tdefault: \"" + this.defaultVariant + "\"\n" +
                "\t\t}";
    }
}
