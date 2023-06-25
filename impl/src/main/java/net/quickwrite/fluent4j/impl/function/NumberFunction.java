package net.quickwrite.fluent4j.impl.function;

import com.ibm.icu.number.*;
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

public class NumberFunction<B extends ResultBuilder> implements FluentFunction<B> {
    private static final NumberFunction<?> DEFAULT = new NumberFunction<>();

    @Override
    public String getIdentifier() {
        return "NUMBER";
    }

    @Override
    public FluentPlaceable<B> parseFunction(final FluentScope<B> scope, final ArgumentList<B> argumentList) {
        final FluentPattern<B> pattern;
        try {
            pattern = argumentList.getArgument(0).unwrap(scope);
        } catch (final FluentPatternException exception) {
            return new FluentTextElement<>("{NUMBER()}");
        }

        final FormattedNumberLiteral<B> numberLiteral;
        if (pattern instanceof FluentNumberLiteral) {
            numberLiteral = new FormattedNumberLiteral<>((FluentNumberLiteral<B>) pattern);
        } else {
            numberLiteral = new FormattedNumberLiteral<>(pattern.toSimpleString(scope));
        }

        final FluentPattern<B> useGrouping = argumentList.getArgument("useGrouping");
        if (useGrouping != null) {
            numberLiteral.useGrouping = useGrouping.toSimpleString(scope).toUpperCase();
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

    @SuppressWarnings("unchecked")
    public static <B extends ResultBuilder> NumberFunction<B> getDefault() {
        return (NumberFunction<B>) DEFAULT;
    }

    private int getIntegerValue(final FluentPattern<B> argument, final FluentScope<B> scope, final int defaultValue) {
        if (argument != null) {
            try {
                return Integer.parseInt(argument.toSimpleString(scope));
            } catch (final NumberFormatException ignored) {

            }
        }

        return defaultValue;
    }

    private static class FormattedNumberLiteral<B extends ResultBuilder> extends FluentNumberLiteral<B> {
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

        public FormattedNumberLiteral(final FluentNumberLiteral<B> numberLiteral) {
            super(numberLiteral);
        }

        @Override
        public void resolve(final FluentScope<B> scope, final B builder) {
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
