package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.StringLiteral;
import net.quickwrite.fluent4j.util.args.FluentArgs;

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

    public FluentVariant(FluentAttribute content) {
        this.identifier = getIdentifier(content.identifier);
        this.content = content;
    }

    private FluentPlaceable getIdentifier(String slice) {
        if (Character.isDigit(slice.charAt(0))) {
            return NumberLiteral.getNumberLiteral(slice);
        }

        return new StringLiteral(slice);
    }

    public FluentPlaceable getIdentifier() {
        return this.identifier;
    }

    public CharSequence getResult(final FluentBundle bundle, final FluentArgs arguments) {
        return this.content.getResult(bundle, arguments);
    }

    @Override
    public String toString() {
        return "FluentVariant: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
