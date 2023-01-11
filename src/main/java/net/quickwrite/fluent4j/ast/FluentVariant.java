package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.StringLiteral;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

/**
 * A variant stores a single variant of a
 * Fluent Select Expression.
 *
 * <p>
 * The variants are denoted as
 * {@code [&lt;condition&gt;] &lt;The text&gt;}
 * and when they should be the default case they
 * have a star in front
 * {@code *[&lt;condition&gt;] &lt;The text&gt;}
 */
public class FluentVariant implements FluentElement {
    private final FluentPlaceable identifier;
    private final FluentAttribute content;

    public FluentVariant(final FluentAttribute content) {
        this.identifier = getIdentifier(content.identifier);
        this.content = content;
    }

    private FluentPlaceable getIdentifier(final String slice) {
        if (Character.isDigit(slice.charAt(0))) {
            try {
                return NumberLiteral.getNumberLiteral(slice);
            } catch (final NumberFormatException ignored) {
                throw new FluentParseException("Expected Number but got \"" + slice + "\"");
            }
        }

        return new StringLiteral(slice);
    }

    public FluentPlaceable getIdentifier() {
        return this.identifier;
    }

    @Override
    public boolean matches(final DirectFluentBundle bundle, final FluentElement selector) {
        return false;
    }

    @Override
    public String stringValue() {
        return identifier.stringValue();
    }

    public CharSequence getResult(final AccessorBundle bundle, final int recursionDepth) {
        return this.content.getResult(bundle, recursionDepth - 1);
    }

    @Override
    public String toString() {
        return "FluentVariant: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
