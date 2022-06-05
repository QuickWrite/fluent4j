package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;

/**
 * The number literal stores numbers. These numbers
 * are stored in two different containers depending
 * on their type.
 *
 * <p>
 * Numbers can be integers or rational numbers
 * </p>
 */
public class NumberLiteral<T extends Number> implements FluentPlaceable, FluentSelectable {
    private final T number;

    private NumberLiteral(T number) {
        this.number = number;
    }

    public static NumberLiteral<? extends Number> getNumberLiteral(StringSlice slice) {
        try {
            return new NumberLiteral<>(Integer.parseInt(slice.toString()));
        } catch (NumberFormatException ignored) {
        }

        try {
            return new NumberLiteral<>(Double.parseDouble(slice.toString()));
        } catch (NumberFormatException ignored) {
        }

        throw new FluentParseException("Number", slice.toString(), slice.getPosition());
    }

    public T getNumber() {
        return this.number;
    }

    @Override
    public StringSlice getContent() {
        return null;
    }

    @Override
    public String toString() {
        return "FluentNumberLiteral: {\n" +
                "\t\t\tvalue: \"" + this.getNumber() + "\"\n" +
                "\t\t}";
    }
}
