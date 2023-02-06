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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

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

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        final String formattedNumber = NumberFormatter.withLocale(scope.getBundle().getLocale()).format(number).toString();

        appendable.append(formattedNumber);
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
    public Function<FluentSelect.FluentVariant, Boolean> selectChecker(final FluentScope scope) {
        return (variant) -> {
            FluentSelect.FluentVariant.FluentVariantKey variantKey = variant.getIdentifier().getSimpleIdentifier();

            if (variantKey instanceof FluentNumberLiteral) {
                return ((FluentNumberLiteral) variantKey).number.compareTo(number) == 0;
            }

            try {
                final String identifier = variantKey.toSimpleString(scope);

                return PluralRules.forLocale(scope.getBundle().getLocale()).select(formattedNumber).equals(identifier);
            } catch (final IOException exception) {
                throw new RuntimeException(exception);
            }
        };
    }
}
