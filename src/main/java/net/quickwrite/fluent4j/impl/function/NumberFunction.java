package net.quickwrite.fluent4j.impl.function;

import com.ibm.icu.number.*;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;

import java.io.IOException;

public class NumberFunction implements FluentFunction {
    @Override
    public String getIdentifier() {
        return "NUMBER";
    }

    @Override
    public FluentPlaceable parseFunction(final FluentScope scope, final ArgumentList argumentList) {
        final FormattedNumberLiteral numberLiteral;
        try {
            final FluentPattern pattern = argumentList.getArgument(0).unwrap(scope);

            final String stringValue = pattern.toSimpleString(scope);

            numberLiteral = new FormattedNumberLiteral(stringValue);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        final FluentPattern useGrouping = argumentList.getArgument("useGrouping");
        if (useGrouping != null) {
            try {
                numberLiteral.useGrouping = useGrouping.toSimpleString(scope).toUpperCase();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        numberLiteral.minimumFractionDigits = getIntegerValue(
                argumentList.getArgument("minimumFractionDigits"),
                scope,
                0
        );

        numberLiteral.maximumFractionDigits = getIntegerValue(
                argumentList.getArgument("maximumFractionDigits"),
                scope,
                999
        );

        numberLiteral.minimumIntegerDigits = getIntegerValue(
                argumentList.getArgument("minimumIntegerDigits"),
                scope,
                0
        );

        return numberLiteral;
    }

    private int getIntegerValue(final FluentPattern argument, final FluentScope scope, final int defaultValue) {
        if (argument != null) {
            try {
                 return Integer.parseInt(argument.toSimpleString(scope));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } catch (final NumberFormatException ignored) {

            }
        }

        return defaultValue;
    }

    private static class FormattedNumberLiteral extends FluentNumberLiteral {
        private String useGrouping = "OFF";
        private int minimumFractionDigits;
        private int maximumFractionDigits;
        private int minimumIntegerDigits;

        public FormattedNumberLiteral(final String number) {
            super(number);
        }

        @Override
        public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
            LocalizedNumberFormatter numberFormatter = NumberFormatter.with().locale(scope.getBundle().getLocale()).precision(Precision.minMaxFraction(minimumFractionDigits, maximumFractionDigits))
                    .integerWidth(IntegerWidth.zeroFillTo(minimumIntegerDigits));

            try {
                numberFormatter = numberFormatter.grouping(NumberFormatter.GroupingStrategy.valueOf(useGrouping));
            } catch (final IllegalArgumentException ignored) {

            }

            numberFormatter.format(number).appendTo(appendable);
        }
    }
}
