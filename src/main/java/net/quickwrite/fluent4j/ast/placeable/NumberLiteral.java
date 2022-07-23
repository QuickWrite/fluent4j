package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

/**
 * The number literal stores numbers. These numbers
 * are stored in two different containers depending
 * on their type.
 *
 * <p>
 * Numbers can be integers or rational numbers
 * </p>
 */
public class NumberLiteral<T extends Number> implements FluentPlaceable, FluentSelectable, FluentArgument<T> {
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

        throw new FluentParseException("Number", slice.toString(), slice.getAbsolutePosition());
    }

    @Override
    public StringSlice getContent() {
        return null;
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        return number.toString();
    }

    @Override
    public T valueOf() {
        return this.number;
    }

    @Override
    public boolean matches(String selector) {
        return selector.equals(this.number.toString());
    }

    @Override
    public String stringValue() {
        return this.number.toString();
    }

    @Override
    public String toString() {
        return "FluentNumberLiteral: {\n" +
                "\t\t\tvalue: \"" + this.valueOf() + "\"\n" +
                "\t\t}";
    }
}
