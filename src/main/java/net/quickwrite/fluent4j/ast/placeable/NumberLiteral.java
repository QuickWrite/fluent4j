package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

import java.util.Comparator;

/**
 * The number literal stores numbers. These numbers
 * are stored in two different containers depending
 * on their type.
 *
 * <p>
 * Numbers can be integers or rational numbers
 * </p>
 */
public class NumberLiteral<T extends Number & Comparable<T>> implements FluentPlaceable, FluentSelectable, FluentArgument<T>, Comparator<T> {
    private final T number;
    private final String stringValue;

    private NumberLiteral(T number) {
        this.number = number;
        this.stringValue = number.toString();
    }

    private NumberLiteral(T number, String stringValue) {
        this.number = number;
        this.stringValue = stringValue;
    }

    public static NumberLiteral<? extends Number> getNumberLiteral(StringSlice slice) {
        String stringValue = slice.toString();

        try {
            double dble = Double.parseDouble(stringValue);

            // checks if the double is an integer
            if ((dble % 1) == 0) {
                return new NumberLiteral<>((int)dble, stringValue);
            }

            return new NumberLiteral<>(dble, stringValue);
        } catch (NumberFormatException ignored) {
        }

        throw new FluentParseException("Number", stringValue, slice.getAbsolutePosition());
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
    public boolean matches(FluentArgument<?> selector) {
        if (selector instanceof NumberLiteral) {
            return matches((T) selector.valueOf());
        }

        return selector.stringValue().equals(this.stringValue);
    }

    public boolean matches(T selector) {
        try {
            return compare(number, selector) == 0;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    public int compare(T a, T b) throws ClassCastException {
        return a.compareTo(b);
    }

    @Override
    public String stringValue() {
        return this.stringValue;
    }

    @Override
    public String toString() {
        return "FluentNumberLiteral: {\n" +
                "\t\t\tvalue: " + this.valueOf() + "\n" +
                "\t\t\tstringValue: \"" + this.stringValue + "\"\n" +
                "\t\t}";
    }
}
