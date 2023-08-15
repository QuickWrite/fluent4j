package net.quickwrite.fluent4j.impl.function;

import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

public class NumberFunction implements FluentFunction {
    private static final NumberFunction DEFAULT = new NumberFunction();

    @Override
    public String getIdentifier() {
        return "NUMBER";
    }

    @Override
    public FluentPlaceable parseFunction(final FluentScope scope, final ArgumentList argumentList) {
        final FluentPattern pattern;
        try {
            pattern = argumentList.getArgument(0).orElseThrow().unwrap(scope);
        } catch (final FluentPatternException | NoSuchElementException exception) {
            return new FluentTextElement("{NUMBER()}");
        }

        final FormattedNumberLiteral numberLiteral;
        if (pattern instanceof FluentNumberLiteral) {
            numberLiteral = new FormattedNumberLiteral((FluentNumberLiteral) pattern);
        } else {
            numberLiteral = new FormattedNumberLiteral(pattern.toSimpleString(scope));
        }

        final Optional<ArgumentList.NamedArgument> useGrouping = argumentList.getArgument("useGrouping");
        useGrouping.ifPresent(namedArgument -> numberLiteral.useGrouping = namedArgument.toSimpleString(scope).toUpperCase());

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

    public static NumberFunction getDefault() {
        return DEFAULT;
    }

    private int getIntegerValue(final Optional<ArgumentList.NamedArgument> argument, final FluentScope scope, final int defaultValue) {
        if (argument.isPresent()) {
            try {
                return Integer.parseInt(argument.get().toSimpleString(scope));
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

        public FormattedNumberLiteral(final BigDecimal number) {
            super(number);
        }

        public FormattedNumberLiteral(final FluentNumberLiteral numberLiteral) {
            super(numberLiteral);
        }

        @Override
        public void resolve(final FluentScope scope, final ResultBuilder builder) {
            LocalizedNumberFormatter numberFormatter = NumberFormatter.with().locale(scope.bundle().getLocale()).precision(Precision.minMaxFraction(minimumFractionDigits, maximumFractionDigits))
                    .integerWidth(IntegerWidth.zeroFillTo(minimumIntegerDigits));

            try {
                numberFormatter = numberFormatter.grouping(NumberFormatter.GroupingStrategy.valueOf(useGrouping));
            } catch (final IllegalArgumentException ignored) {

            }

            builder.append(numberFormatter.format(number));
        }
    }
}
