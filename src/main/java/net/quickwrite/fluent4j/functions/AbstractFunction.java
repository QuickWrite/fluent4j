package net.quickwrite.fluent4j.functions;

import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;

/**
 * Functions provide additional functionality available to the localizers.
 *
 * <p>
 * They can be either used to format data according to the current language's rules
 * or can provide additional data that the localizer may use (like, the platform,
 * or time of the day) to fine tune the translation.
 */
public abstract class AbstractFunction {
    private final String identifier;

    /**
     * The initializer of the function.
     * <br>
     * <hr>
     *
     * <p>
     * The identifier of the function will be changed to
     * {@code UPPERCASE} even if the identifier is completely
     * lowercase. If the function wouldn't be uppercase it cannot
     * be called at all as function identifiers are defined
     * tp be completely uppercase and so wouldn't be parsed.
     * <br>
     * So the identifier {@code hello} would result in {@code HELLO}.
     * </p>
     *
     * @param identifier The identifier on how the function should be called.
     */
    public AbstractFunction(final String identifier) {
        this.identifier = identifier.toUpperCase();
    }

    /**
     * Returns the identifier of the function so
     * that it can be used to check if it is
     * the correct function to be called.
     *
     * @return The identifier of the function
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Returns the value that should result from the
     * specific argument or the bundle itself. <br>
     * (For example the language or a single parameter)
     *
     * <p>
     * So when the function is an {@code ADD()} function
     * it could have two different named arguments that
     * are added inside of the result function and then
     * returned as a {@link NumberLiteral}. <br>
     * So when the function is implemented this:
     * <pre>
     *     test = { $n1 } + { $n2 } = { ADD($n1, $n2) }
     * </pre>
     * with the parameters {@code $n1 = 26} and {@code $n2 = 16}
     * would result in {@code 26 + 16 = 42}.
     *
     * @param bundle The bundle that this is getting called from
     * @param arguments The arguments the function gets
     * @return The result
     */
    public abstract FluentPlaceable getResult(final DirectFluentBundle bundle, final FluentArgs arguments);
}
