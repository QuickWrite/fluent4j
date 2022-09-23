package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FunctionFluentArguments;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

/**
 * Implements the basis for a value that gets
 * parameters and calls another element of data so
 * that it can operate.
 */
public abstract class FluentFunction implements FluentPlaceable, FluentArgumentResult {
    protected final String functionName;
    protected final FluentArgs arguments;

    public FluentFunction(final StringSlice functionName, final FluentArgs arguments) {
        this(functionName.toString(), arguments);
    }

    public FluentFunction(final String functionName, final FluentArgs arguments) {
        this.functionName = functionName;
        if (!check(functionName)) {
            // TODO: Better Error handling
            throw new FluentParseException("The callee has to be an upper-case identifier or a term");
        }

        this.arguments = arguments;
    }

    /**
     * Returns the arguments that the function
     * has itself in a sanitized form.
     *
     * @param bundle    The bundle that this is being called from
     * @param arguments The arguments in this scope
     * @return The sanitized arguments of the function
     */
    protected FluentArgs getArguments(final DirectFluentBundle bundle, final FluentArgs arguments) {
        if (this.arguments != null)
            this.arguments.sanitize(bundle, arguments);

        return this.arguments;
    }

    /**
     * Returns the {@link FluentElement} that the function is returning.
     *
     * @param bundle    The bundle that this is being called from
     * @param arguments The arguments that are passed into this function
     * @return The resulting {@link FluentElement} that has been created
     */
    public abstract FluentElement getArgumentResult(final DirectFluentBundle bundle, final FluentArgs arguments);

    /**
     * Checks if this FluentFunction and the selector are the same.
     *
     * @param bundle   The base bundle
     * @param selector The other element
     * @return If they are the same object
     */
    @Override
    public boolean matches(final DirectFluentBundle bundle, final FluentElement selector) {
        return this.equals(selector);
    }

    /**
     * Returns the function name that is being
     * used.
     *
     * @return The {@code functionName}
     */
    @Override
    public String stringValue() {
        return this.functionName;
    }

    /**
     * Checks if the name is a correct function name.
     *
     * @param string The function name
     * @return If it is a correct function name
     */
    protected abstract boolean check(final String string);
}
