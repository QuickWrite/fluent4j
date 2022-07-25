package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * The number literal stores numbers. These numbers
 * are stored in two different containers depending
 * on their type.
 *
 * <p>
 * Numbers can be integers or rational numbers
 * </p>
 */
public class NumberLiteral implements FluentPlaceable, FluentSelectable, FluentArgument<Number> {
    protected final Number number;
    protected final String stringValue;

    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.ENGLISH);

    protected NumberLiteral(final Number number) {
        this(number, number.toString());
    }

    protected NumberLiteral(final Number number, final String stringValue) {
        this.number = number;
        this.stringValue = stringValue;
    }

    public static NumberLiteral getNumberLiteral(final StringSlice slice) {
        String stringValue = slice.toString();

        try {
            return new NumberLiteral(numberFormatter.parse(stringValue), stringValue);
        } catch (ParseException exception) {
            throw new FluentParseException("Number", stringValue, slice.getAbsolutePosition());
        }
    }

    @Override
    public StringSlice getContent() {
        return new StringSlice(this.stringValue);
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        return NumberFormat.getInstance(bundle.getLocale()).format(number);
    }

    @Override
    public Number valueOf() {
        return this.number;
    }

    @Override
    public boolean matches(final FluentArgument<?> selector) {
        if (selector instanceof NumberLiteral) {
            return matches((Number)selector.valueOf());
        }

        return selector.stringValue().equals(this.stringValue);
    }

    public boolean matches(final Number selector) {
        return selector.equals(this.number);
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
