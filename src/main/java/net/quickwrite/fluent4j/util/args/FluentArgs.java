package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

public interface FluentArgs {
    /**
     * An initialized object that has no values
     * so that it does not need to be created multiple
     * times and can be easily accessed.
     */
    FluentArgs EMPTY_ARGS = new ResourceFluentArguments();

    /**
     * Changes the way some values point so that there are no
     * loops or undefined variables.
     *
     * <p>
     * This is necessary if the scopes are getting changed as a new scope does not have access
     * every variable the previous scope had.
     *
     * @param bundle The main bundle
     * @param arguments The old arguments
     */
    void sanitize(final DirectFluentBundle bundle, final FluentArgs arguments);

    /**
     * Returns the {@link FluentArgument} with the given
     * position in the positional arguments.
     *
     * @param index The position the argument has
     * @return The argument itself
     */
    FluentArgument getPositional(final int index);

    /**
     * Adds a new named argument to the named arguments.
     *
     * <p>
     * When this argument already exists it will be overwritten.
     *
     * @param key The name of the named argument
     * @param argument The argument value
     */
    void setNamed(final String key, final FluentArgument argument);

    /**
     * Returns a named argument with the {@code key}.
     *
     * @param key The name of the argument
     * @return The value of the named argument
     */
    FluentArgument getNamed(final String key);

    /**
     * Adds a new positional argument at the end.
     *
     * @param argument The argument itself
     */
    void addPositional(final FluentArgument argument);
}
