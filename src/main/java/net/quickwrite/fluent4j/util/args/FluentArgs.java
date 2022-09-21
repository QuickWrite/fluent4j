package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

import java.util.Set;

public interface FluentArgs {
    /**
     * An initialized object that has no values
     * so that it does not need to be created multiple
     * times and can be easily accessed.
     */
    FluentArgs EMPTY_ARGS = new FluentArguments();

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
     * Adds a new named argument to the named arguments.
     *
     * <p>
     * When this argument already exists it will be overwritten.
     *
     * @param key The name of the named argument
     * @param argument The argument value
     */
    void setNamed(final String key, final FluentElement argument);

    /**
     * Returns a named argument with the {@code key}.
     *
     * @param key The name of the argument
     * @return The value of the named argument
     */
    FluentElement getNamed(final String key);

    /**
     * Returns the keys that the named parameters have.
     *
     * @return The keys that the named parameters have
     */
    Set<String> getNamedKeys();

    /**
     * Checks if there are no arguments inside of the
     * object itself.
     *
     * @return if no argument exist
     */
    boolean isEmpty();
}
