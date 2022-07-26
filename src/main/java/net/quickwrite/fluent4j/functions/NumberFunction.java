package net.quickwrite.fluent4j.functions;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.CustomNumberLiteral;
import net.quickwrite.fluent4j.util.args.FluentArgs;

import java.text.ParseException;

public class NumberFunction extends AbstractFunction {
    public NumberFunction() {
        super("NUMBER");
    }

    @Override
    public FluentPlaceable<?> getResult(FluentBundle bundle, FluentArgs arguments) {
        System.out.println(arguments);

        Object number = arguments.getPositional(0).valueOf();

        CustomNumberLiteral numberLiteral;
        if (number instanceof Number) {
            numberLiteral = new CustomNumberLiteral((Number) number);
        } else {
            try {
                numberLiteral = new CustomNumberLiteral(number.toString());
            } catch (ParseException exception) {
                throw new NumberFormatException();
            }
        }

        numberLiteral.setMinimumFractionDigits(getIntValue("minimumFractionDigits", 0, arguments));
        numberLiteral.setMaximumFractionDigits(getIntValue("maximumFractionDigits", Integer.MAX_VALUE, arguments));
        numberLiteral.setMinimumIntegerDigits(getIntValue("minimumIntegerDigits", 0, arguments));
        numberLiteral.setUseGrouping(getBooleanValue("useGrouping", true, arguments));

        // TODO: Add "currencyDisplay"

        return numberLiteral;
    }

    private int getIntValue(final String key, final int defaultValue, final FluentArgs arguments) {
        return arguments
                .getOrDefault(key, defaultValue, Number.class)
                .valueOf()
                .intValue();
    }

    private boolean getBooleanValue(final String key, final boolean defaultValue, final FluentArgs arguments) {
        return !arguments
                .getOrDefault(key, String.valueOf(defaultValue), String.class)
                .valueOf()
                .equals("false");
    }
}
