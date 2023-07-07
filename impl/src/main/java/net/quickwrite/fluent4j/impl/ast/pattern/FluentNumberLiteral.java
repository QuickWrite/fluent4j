package net.quickwrite.fluent4j.impl.ast.pattern;

import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.math.BigDecimal;
import java.util.function.Function;

public class FluentNumberLiteral<B extends ResultBuilder> implements
        FluentPlaceable<B>,
        ArgumentList.NamedArgument<B>,
        FluentSelect.Selectable<B>,
        FluentSelect.FluentVariant.FluentVariantKey<B>
{
    protected final String stringNumber;
    protected final BigDecimal number;
    protected final FormattedNumber formattedNumber;

    private static final LocalizedNumberFormatter NUMBER_FORMATTER = NumberFormatter.withLocale(ULocale.ENGLISH);

    public FluentNumberLiteral(final String number) {
        this.stringNumber = number;

        this.number = new BigDecimal(number);
        this.formattedNumber = NUMBER_FORMATTER.format(this.number);
    }

    public FluentNumberLiteral(final BigDecimal number) {
        this.stringNumber = number.toString();

        this.number = number;
        this.formattedNumber = NUMBER_FORMATTER.format(this.number);
    }

    public FluentNumberLiteral(final long number) {
        this(new BigDecimal(number));
    }

    public FluentNumberLiteral(final double number) {
        this(new BigDecimal(Double.toString(number)));
    }

    public FluentNumberLiteral(final FluentNumberLiteral<B> numberLiteral) {
        this.stringNumber = numberLiteral.stringNumber;

        this.number = numberLiteral.number;
        this.formattedNumber = numberLiteral.formattedNumber;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        final String formattedNumber = NumberFormatter.withLocale(scope.bundle().getLocale()).format(number).toString();

        builder.append(formattedNumber);
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return this.stringNumber;
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return this;
    }

    @Override
    public Function<FluentSelect.FluentVariant<B>, Boolean> selectChecker(final FluentScope<B> scope) {
        return (variant) -> {
            final FluentSelect.FluentVariant.FluentVariantKey<B> variantKey = variant.getIdentifier().getSimpleIdentifier();

            if (variantKey instanceof FluentNumberLiteral) {
                return ((FluentNumberLiteral<B>) variantKey).number.compareTo(number) == 0;
            }

            final String identifier = variantKey.toSimpleString(scope);
            return PluralRules.forLocale(scope.bundle().getLocale()).select(formattedNumber).equals(identifier);
        };
    }
}
