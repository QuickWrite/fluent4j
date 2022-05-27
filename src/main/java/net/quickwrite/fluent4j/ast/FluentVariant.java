package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.parser.StringSlice;

public class FluentVariant extends FluentElement {
    private final FluentPlaceable identifier;
    private final FluentAttribute content;
    private final boolean defaultVariant;

    public FluentVariant(FluentAttribute content, boolean defaultVariant) {
        this.identifier = getIdentifier(content.identifier);
        this.content = content;
        this.defaultVariant = defaultVariant;
    }

    public boolean isDefault() {
        return this.defaultVariant;
    }

    private FluentPlaceable getIdentifier(StringSlice slice) {
        if (Character.isDigit(slice.getChar())) {
            return new FluentPlaceable.NumberLiteral(slice);
        }

        return new FluentPlaceable.StringLiteral(slice);
    }

    @Override
    public String toString() {
        return "FluentVariant: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\tdefault: " + this.defaultVariant + "\n" +
                "\t\t}";
    }
}
