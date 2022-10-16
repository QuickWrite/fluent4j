package net.quickwrite.fluent4j.functions;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FunctionFluentArgs;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.args.CustomNumberLiteral;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

import java.text.ParseException;

/**
 * The implementation of the {@code NUMBER()} builtin
 * function available to translations.
 */
public class NumberFunction extends AbstractFunction {
    /**
     * <p>
     * The {@code NUMBER()} function that generates a
     * {@link CustomNumberLiteral} with the specified options.
     * </p>
     * <hr>
     * <p>
     * Translations may call the `NUMBER()` builtin in order to specify formatting
     * options of a number. For example:
     * <pre>
     *     pi = The value of π is {NUMBER($pi, maximumFractionDigits: 2)}.
     * </pre>
     * </p>
     * <p>
     * The implementation expects an array of `FluentValues` representing the
     * positional arguments, and an object of named `FluentValues` representing the
     * named parameters.
     * </p>
     * <p>
     * The following options are recognized:
     * <pre>
     *     useGrouping
     *     minimumIntegerDigits
     *     minimumFractionDigits
     *     maximumFractionDigits
     * </pre>
     * </p>
     * <p>
     * Other options are ignored.
     * </p>
     */
    public NumberFunction() {
        super("NUMBER");
    }

    /**
     * Changes any value into a number and / or is
     * adding different parameters to the number itself.
     * <p>
     * The {@code NUMBER()} function is accepting a single
     * positional argument and all of the modifiers that
     * a {@link CustomNumberLiteral} allows. <br>
     * So it could be used like this:
     * <pre>
     *     test = This has 3 decimal places: { NUMBER(42, minimumFractionDigits: 3) }
     * </pre>
     * and {@code test} would return {@code This has 3 decimal places: 42.000} when the
     * locale is set to english.
     * </p>
     * <p>
     * If the number is already a {@link CustomNumberLiteral} it is
     * removing all of the custom arguments.
     * </p>
     *
     * @param bundle    The bundle that this is getting called from
     * @param arguments The arguments the function gets
     * @return The number as a {@link CustomNumberLiteral} with the parameters
     */
    @Override
    public FluentPlaceable getResult(final AccessorBundle bundle, final FunctionFluentArgs arguments) {
        final FluentElement number = arguments.getPositional(0);

        CustomNumberLiteral numberLiteral;
        if (number instanceof NumberLiteral) {
            numberLiteral = new CustomNumberLiteral(((NumberLiteral) number).valueOf());
        } else {
            try {
                numberLiteral = new CustomNumberLiteral(number.stringValue());
            } catch (final ParseException exception) {
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
        final FluentElement argument = arguments.getNamed(key);

        if (!(argument instanceof NumberLiteral)) {
            try {
                return Integer.parseInt(argument.stringValue());
            } catch (Exception ignored) {
                return defaultValue;
            }
        }

        return ((NumberLiteral) argument).valueOf().intValue();
    }

    private boolean getBooleanValue(final String key, final boolean defaultValue, final FluentArgs arguments) {
        final FluentElement argument = arguments.getNamed(key);

        try {
            return Boolean.parseBoolean(argument.stringValue());
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
}
