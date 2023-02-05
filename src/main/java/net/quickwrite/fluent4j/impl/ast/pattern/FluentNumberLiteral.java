package net.quickwrite.fluent4j.impl.ast.pattern;

import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

public class FluentNumberLiteral implements FluentPlaceable, ArgumentList.NamedArgument, FluentSelect.Selectable {
    private final String stringNumber;
    private final Number number;
    private final FormattedNumber formattedNumber;

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
    public Function<FluentSelect.FluentVariant, Boolean> selectChecker(final FluentScope scope) {
        return (variant) -> {
            final String identifier = variant.getIdentifier().getSimpleIdentifier();

            if (stringNumber.equals(identifier)) {
                return true;
            }

            return PluralRules.forLocale(scope.getBundle().getLocale()).select(formattedNumber).equals(identifier);
        };
    }
}
