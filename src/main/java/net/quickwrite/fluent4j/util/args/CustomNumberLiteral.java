package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;

import java.text.NumberFormat;

public class CustomNumberLiteral extends NumberLiteral {
    private final int minimumFractionDigits;
    private final int maximumFractionDigits;
    private final int minimumIntegerDigits;
    private final boolean useGrouping;

    public CustomNumberLiteral(final Number number, final FluentArgs arguments) {
        this(number, number.toString(), arguments);
    }

    public CustomNumberLiteral(final Number number, final String stringValue, final FluentArgs arguments) {
        super(number, stringValue);

        // TODO: Move this into the NUMBER function and add setter
        this.minimumFractionDigits = getIntValue("minimumFractionDigits", 0, arguments);
        this.maximumFractionDigits = getIntValue("maximumFractionDigits", Integer.MAX_VALUE, arguments);
        this.minimumIntegerDigits = getIntValue("minimumIntegerDigits", 0, arguments);
        this.useGrouping = getBooleanValue("useGrouping", true, arguments);

        // TODO: Add "currencyDisplay"
    }

    private int getIntValue(final String key, final int defaultValue, final FluentArgs arguments) {
        return arguments
                .getOrDefault(key, (Number)defaultValue)
                .valueOf()
                .intValue();
    }

    private boolean getBooleanValue(final String key, final boolean defaultValue, final FluentArgs arguments) {
        return !arguments
                .getOrDefault(key, String.valueOf(defaultValue))
                .valueOf()
                .equals("false");
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        NumberFormat numberFormat = NumberFormat.getInstance(bundle.getLocale());
        numberFormat.setGroupingUsed(useGrouping);
        numberFormat.setMinimumFractionDigits(minimumFractionDigits);
        numberFormat.setMaximumFractionDigits(maximumFractionDigits);

        numberFormat.setMinimumIntegerDigits(minimumIntegerDigits);

        return numberFormat.format(number);
    }
}
