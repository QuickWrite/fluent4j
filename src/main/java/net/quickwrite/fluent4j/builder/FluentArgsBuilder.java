package net.quickwrite.fluent4j.builder;

import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.StringLiteral;
import net.quickwrite.fluent4j.functions.NumberFunction;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

/**
 * The builder class for the {@link FluentArgs} class.
 */
public class FluentArgsBuilder extends AbstractBuilder<FluentArgs> {
    /**
     * Creates a new FluentArgsBuilder for the {@link FluentArgs} class.
     *
     * <p>
     * FluentArgs are used so that different scopes can use different
     * values and so that messages can have variety.
     * <p>
     * There are two different types of arguments:
     *     <ol>
     *         <li>
     *             Positional <br>
     *             Arguments without a keyword that are normally used in
     *             functions as a first parameter that is always used.
     *             <hr>
     *             For example the {@link NumberFunction} needs a single
     *             positional parameter so that it can do something with
     *             that number.
     *         </li>
     *         <li>
     *             Named <br>
     *             Arguments with a keyword that are normally used as
     *             variables or custom parameters for functions.
     *             <hr>
     *             For example the {@link NumberFunction} allows different
     *             named parameters like {@code maximumFractionDigits} that
     *             allows the number to be formatted in a custom way.
     *             (In this case the number would only have {@code x} fraction
     *             digits) <br>
     *             It also is used as different parameters in things like
     *             terms or messages, which can be accessed using variables.
     *         </li>
     *     </ol>
     */
    public FluentArgsBuilder() {
        super(new FluentArgs());
    }

    /**
     * Adds a named {@link FluentArgument} to the {@link FluentArgs} that can be accessed.
     *
     * @param key      The key that is used to access this argument
     * @param argument The argument itself with all of it's data
     * @return The FluentArgsBuilder object itself
     */
    public FluentArgsBuilder setNamed(final String key, final FluentArgument argument) {
        this.element.setNamed(key, argument);

        return this;
    }

    /**
     * Adds a named {@link String} to the {@link FluentArgs} that can be accessed.
     *
     * @param key      The key that is used to access this argument
     * @param argument The argument itself with all of it's data
     * @return The FluentArgsBuilder object itself
     */
    public FluentArgsBuilder setNamed(final String key, final String argument) {
        return this.setNamed(key, new StringLiteral(argument));
    }

    /**
     * Adds a named {@link Number} to the {@link FluentArgs} that can be accessed.
     *
     * @param key      The key that is used to access this argument
     * @param argument The argument itself with all of it's data
     * @return The FluentArgsBuilder object itself
     */
    public FluentArgsBuilder setNamed(final String key, final Number argument) {
        return this.setNamed(key, new NumberLiteral(argument));
    }

    /**
     * Adds a positional argument to the argument list.
     *
     * <p>
     * The argument will always be added at the end and cannot be rearranged.
     *
     * @param argument The argument itself with all of it's data
     * @return The FluentArgsBuilder object itself
     */
    public FluentArgsBuilder addPositional(final FluentArgument argument) {
        this.element.addPositional(argument);

        return this;
    }

    /**
     * Adds a positional {@link String} argument to the argument list.
     *
     * <p>
     * The argument will always be added at the end and cannot be rearranged.
     *
     * @param argument The argument itself with all of it's data
     * @return The FluentArgsBuilder object itself
     */
    public FluentArgsBuilder addPositional(final String argument) {
        return this.addPositional(new StringLiteral(argument));
    }

    /**
     * Adds a positional {@link Number} argument to the argument list.
     *
     * <p>
     * The argument will always be added at the end and cannot be rearranged.
     *
     * @param argument The argument itself with all of it's data
     * @return The FluentArgsBuilder object itself
     */
    public FluentArgsBuilder addPositional(final Number argument) {
        return this.addPositional(new NumberLiteral(argument));
    }
}
