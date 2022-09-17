package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.ast.FluentElement;

public interface FunctionFluentArgs extends FluentArgs {
    /**
     * Adds a new positional argument at the end.
     *
     * @param argument The argument itself
     */
    void addPositional(final FluentElement argument);

    /**
     * Returns the {@link FluentElement} with the given
     * position in the positional arguments.
     *
     * @param index The position the argument has
     * @return The argument itself
     */
    FluentElement getPositional(final int index);

    /**
     * Returns the amount of positional
     * parameters the object has.
     *
     * @return The amount of positional parameters
     */
    int getPositionalSize();
}
