package net.quickwrite.fluent4j.ast.placeable;

import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

import java.math.BigDecimal;

/**
 * The number literal stores numbers. These numbers
 * are stored in two different containers depending
 * on their type.
 *
 * <p>
 * Numbers can be integers or rational numbers
 * </p>
 */
public class NumberLiteral implements FluentPlaceable<Number>, FluentSelectable {
    protected final Number number;
    protected final String stringValue;
    protected final FormattedNumber formattedNumber;

    private static final LocalizedNumberFormatter numberFormatter = NumberFormatter.withLocale(ULocale.ENGLISH);

    protected NumberLiteral(final Number number) {
        this(number, number.toString());
    }

    protected NumberLiteral(final Number number, final String stringValue) {
        this.number = number;
        this.stringValue = stringValue;
        this.formattedNumber = numberFormatter.format(number);
    }

    public static NumberLiteral getNumberLiteral(final StringSlice slice) {
        return getNumberLiteral(slice.toString());
    }

    public static NumberLiteral getNumberLiteral(final String value) {
        return new NumberLiteral(new BigDecimal(value), value);
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
    public boolean matches(final FluentBundle bundle, final FluentArgument<?> selector) {
        if (selector instanceof NumberLiteral) {
            return matches((Number)selector.valueOf());
        }

        if (PluralRules.forLocale(bundle.getLocale()).select(this.formattedNumber).equals(selector.stringValue())) {
            return true;
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
