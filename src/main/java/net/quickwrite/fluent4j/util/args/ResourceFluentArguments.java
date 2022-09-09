package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import net.quickwrite.fluent4j.util.bundle.ResourceFluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;

import java.util.*;

/**
 * A storage for the different arguments that
 * are used as the parameters when a message, term or a function
 * is getting accessed.
 */
public class ResourceFluentArguments implements FluentArgs {
    private final Map<String, FluentArgument> namedArguments;
    private final List<FluentArgument> positionalArguments;

    /**
     * Creates a new empty argument
     * container.
     */
    public ResourceFluentArguments() {
        this(new HashMap<>(), new ArrayList<>());
    }

    /**
     * Creates a new argument container with the given
     * arguments.
     *
     * @param namedArguments The named arguments
     * @param positionalArguments The positional arguments
     */
    public ResourceFluentArguments(final Map<String, FluentArgument> namedArguments, final List<FluentArgument> positionalArguments) {
        this.namedArguments = namedArguments;
        this.positionalArguments = positionalArguments;
    }

    @Override
    public void sanitize(final ResourceFluentBundle bundle, final FluentArgs arguments) {
        for (final String key : namedArguments.keySet()) {
            final FluentArgument argument = namedArguments.get(key);

            if (argument instanceof FluentArgumentResult) {
                namedArguments.put(key, ((FluentArgumentResult) argument).getArgumentResult(bundle, arguments));
            }
        }

        for (int i = 0; i < positionalArguments.size(); i++) {
            final FluentArgument argument = positionalArguments.get(i);

            if (argument instanceof FluentArgumentResult) {
                positionalArguments.set(i, ((FluentArgumentResult) argument).getArgumentResult(bundle, arguments));
            }
        }
    }

    @Override
    public FluentArgument getPositional(final int index) {
        return this.positionalArguments.get(index);
    }

    @Override
    public void setNamed(final String key, final FluentArgument argument) {
        this.namedArguments.put(key, argument);
    }

    @Override
    public FluentArgument getNamed(final String key) {
        return this.namedArguments.get(key);
    }

    @Override
    public void addPositional(final FluentArgument argument) {
        this.positionalArguments.add(argument);
    }

    @Override
    public String toString() {
        return "FluentArgumentList: {\n" +
                "\t\t\tnamedArguments: \"" + this.namedArguments + "\"\n" +
                "\t\t\tpositionalArguments: \"" + this.positionalArguments + "\"\n" +
                "\t\t}";
    }
}
