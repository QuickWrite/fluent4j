package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A storage for the named arguments without the
 * positional arguments.
 */
public class FluentArguments implements FluentArgs {
    private final Map<String, FluentElement> namedArguments;

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
    public void sanitize(final DirectFluentBundle bundle, final FluentArgs arguments) {
        for (final String key : namedArguments.keySet()) {
            final FluentElement argument = namedArguments.get(key);

            if (argument instanceof FluentArgumentResult) {
                namedArguments.put(key, ((FluentArgumentResult) argument).getArgumentResult(bundle, arguments));
            }
        }
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

    @Override
    public String toString() {
        return "ResourceNamedFluentArguments: {\n" +
                "\t\t\tnamedArguments: \"" + this.namedArguments + "\"\n" +
                "\t\t}";
    }
}
