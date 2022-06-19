package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.StringLiteral;
import net.quickwrite.fluent4j.util.StringSlice;

/**
 * A variant stores a single variant of a
 * Fluent Select Expression.
 *
 * <p>
 *     The variants are denoted as
 *     {@code [&lt;condition&gt;] &lt;The text&gt;}
 *     and when they should be the default case they
 *     have a star in front
 *     {@code *[&lt;condition&gt;] &lt;The text&gt;}
 * </p>
 *
 */
public class FluentVariant implements FluentElement {
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
            return NumberLiteral.getNumberLiteral(slice);
        }

        return new StringLiteral(slice);
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
