package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A storage for the named arguments without the
 * positional arguments.
 */
public class FluentArguments implements FluentArgs {
    protected final Map<String, FluentElement> namedArguments;

    /**
     * Creates a new argument container with the given
     * arguments.
     */
    public FluentArguments() {
        this(new HashMap<>());
    }

    /**
     * Creates a new argument container with the given
     * arguments.
     *
     * @param namedArguments The named arguments
     */
    public FluentArguments(final Map<String, FluentElement> namedArguments) {
        this.namedArguments = namedArguments;
    }

    @Override
    public FluentArgs sanitize(final AccessorBundle bundle, final int recursionDepth) {
        final FluentArgs args = new FluentArguments();

        for (final String key : namedArguments.keySet()) {
            final FluentElement argument = namedArguments.get(key);

            if (argument instanceof FluentArgumentResult) {
                args.setNamed(key, ((FluentArgumentResult) argument).getArgumentResult(bundle, recursionDepth - 1));
                continue;
            }

            args.setNamed(key, argument);
        }

        return args;
    }

    @Override
    public void setNamed(final String key, final FluentElement argument) {
        this.namedArguments.put(key, argument);
    }

    @Override
    public FluentElement getNamed(final String key) {
        return this.namedArguments.get(key);
    }

    @Override
    public Set<String> getNamedKeys() {
        return this.namedArguments.keySet();
    }

    @Override
    public boolean isEmpty() {
        return this.namedArguments.isEmpty();
    }

    public FluentArgs clone() throws CloneNotSupportedException {
        return (FluentArgs) super.clone();
    }

    @Override
    public String toString() {
        return "FluentArguments: {\n" +
                "\t\t\tnamedArguments: \"" + this.namedArguments + "\"\n" +
                "\t\t}";
    }
}
