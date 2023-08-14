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

public class FluentNumberLiteral implements
        FluentPlaceable,
        ArgumentList.NamedArgument,
        FluentSelect.Selectable,
        FluentSelect.FluentVariant.FluentVariantKey
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

    public FluentNumberLiteral(final FluentNumberLiteral numberLiteral) {
        this.stringNumber = numberLiteral.stringNumber;

        this.number = numberLiteral.number;
        this.formattedNumber = numberLiteral.formattedNumber;
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        final String formattedNumber = NumberFormatter.withLocale(scope.bundle().getLocale()).format(number).toString();

        builder.append(formattedNumber);
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        return this.stringNumber;
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        return this;
    }

    @Override
    public FluentSelect.FluentVariant select(final FluentScope scope,
                                             final FluentSelect.FluentVariant[] variants,
                                             final FluentSelect.FluentVariant defaultVariant
    ) {
        for (final FluentSelect.FluentVariant variant : variants) {
            final FluentSelect.FluentVariant.FluentVariantKey variantKey = variant.getIdentifier().getSimpleIdentifier();

            if (variantKey instanceof FluentNumberLiteral numberLiteral
                    && numberLiteral.number.compareTo(number) == 0) {
                return variant;
            }

            final String identifier = variantKey.toSimpleString(scope);
            if (PluralRules.forLocale(scope.bundle().getLocale()).select(formattedNumber).equals(identifier)) {
                return variant;
            }
        }

        return defaultVariant;
    }
}
