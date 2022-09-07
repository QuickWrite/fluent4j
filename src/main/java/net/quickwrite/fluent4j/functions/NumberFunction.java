package net.quickwrite.fluent4j.functions;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.args.CustomNumberLiteral;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

import java.text.ParseException;

public class NumberFunction extends AbstractFunction {
    public NumberFunction() {
        super("NUMBER");
    }

    @Override
    public FluentPlaceable getResult(final FluentBundle bundle, final FluentArgs arguments) {
        FluentArgument number = arguments.getPositional(0);

        CustomNumberLiteral numberLiteral;
        if (number instanceof NumberLiteral) {
            numberLiteral = new CustomNumberLiteral(((NumberLiteral) number).valueOf());
        } else {
            try {
                numberLiteral = new CustomNumberLiteral(number.stringValue());
            } catch (ParseException exception) {
                throw new NumberFormatException();
            }
        }

        numberLiteral.setMinimumFractionDigits(getIntValue("minimumFractionDigits", 0, arguments));
        numberLiteral.setMaximumFractionDigits(getIntValue("maximumFractionDigits", Integer.MAX_VALUE, arguments));
        numberLiteral.setMinimumIntegerDigits(getIntValue("minimumIntegerDigits", 0, arguments));
        numberLiteral.setUseGrouping(getBooleanValue("useGrouping", true, arguments));

        return numberLiteral;
    }

    private int getIntValue(final String key, final int defaultValue, final FluentArgs arguments) {
        final FluentArgument argument = arguments.get(key);

        if (!(argument instanceof NumberLiteral)) {
            try {
                return Integer.parseInt(argument.stringValue());
            } catch (Exception ignored) {
                return defaultValue;
            }
        }

        return ((NumberLiteral)argument).valueOf().intValue();
    }

    private boolean getBooleanValue(final String key, final boolean defaultValue, final FluentArgs arguments) {
        final FluentArgument argument = arguments.get(key);

        try {
            return Boolean.parseBoolean(argument.stringValue());
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
}
