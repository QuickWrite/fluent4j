package net.quickwrite.fluent4j.util.args;

import com.ibm.icu.text.NumberFormat;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * The custom number literal stores numbers with different
 * parameters.
 */
public class CustomNumberLiteral extends NumberLiteral {
    private int minimumFractionDigits = 0;
    private int maximumFractionDigits = Integer.MAX_VALUE;
    private int minimumIntegerDigits = 0;
    private boolean useGrouping = true;

    public CustomNumberLiteral(final String number) throws ParseException {
        this(new BigDecimal(number), number);
    }

    public CustomNumberLiteral(final Number number) {
        this(number, number.toString());
    }

    public CustomNumberLiteral(final Number number, final String stringValue) {
        super(number, stringValue);
    }

    public void setMinimumFractionDigits(int minimumFractionDigits) {
        this.minimumFractionDigits = minimumFractionDigits;
    }

    public void setMaximumFractionDigits(int maximumFractionDigits) {
        this.maximumFractionDigits = maximumFractionDigits;
    }

    public void setMinimumIntegerDigits(int minimumIntegerDigits) {
        this.minimumIntegerDigits = minimumIntegerDigits;
    }

    public void setUseGrouping(boolean useGrouping) {
        this.useGrouping = useGrouping;
    }

    @Override
    public String getResult(final AccessorBundle bundle, int recursionDepth) {
        NumberFormat numberFormat = NumberFormat.getInstance(bundle.getBundle().getLocale());
        numberFormat.setGroupingUsed(useGrouping);
        numberFormat.setMaximumFractionDigits(maximumFractionDigits);
        numberFormat.setMinimumFractionDigits(minimumFractionDigits);

        numberFormat.setMinimumIntegerDigits(minimumIntegerDigits);

        return numberFormat.format(number);
    }
}
