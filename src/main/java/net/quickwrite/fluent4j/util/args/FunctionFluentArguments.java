package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;

import java.util.*;

/**
 * A storage for the different arguments that
 * are used as the parameters when a message, term or a function
 * is getting accessed.
 */
public class FunctionFluentArguments implements FunctionFluentArgs {
    private final Map<String, FluentElement> namedArguments;
    private final List<FluentElement> positionalArguments;

    /**
     * Creates a new empty argument
     * container.
     */
    public FunctionFluentArguments() {
        this(new HashMap<>(), new ArrayList<>());
    }

    /**
     * Creates a new argument container with the given
     * arguments.
     *
     * @param namedArguments The named arguments
     * @param positionalArguments The positional arguments
     */
    public FunctionFluentArguments(final Map<String, FluentElement> namedArguments, final List<FluentElement> positionalArguments) {
        this.namedArguments = namedArguments;
        this.positionalArguments = positionalArguments;
    }

    @Override
    public void sanitize(final DirectFluentBundle bundle, final FluentArgs arguments) {
        for (final String key : namedArguments.keySet()) {
            final FluentElement argument = namedArguments.get(key);

            if (argument instanceof FluentArgumentResult) {
                namedArguments.put(key, ((FluentArgumentResult) argument).getArgumentResult(bundle, arguments));
            }
        }

        for (int i = 0; i < positionalArguments.size(); i++) {
            final FluentElement argument = positionalArguments.get(i);

            if (argument instanceof FluentArgumentResult) {
                positionalArguments.set(i, ((FluentArgumentResult) argument).getArgumentResult(bundle, arguments));
            }
        }
    }

    @Override
    public FluentElement getPositional(final int index) {
        if (this.positionalArguments.size() - 1 < index) {
            return null;
        }

        return this.positionalArguments.get(index);
    }

    @Override
    public int getPositionalSize() {
        return this.positionalArguments.size();
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
    public void addPositional(final FluentElement argument) {
        this.positionalArguments.add(argument);
    }

    @Override
    public boolean isEmpty() {
        return this.positionalArguments.isEmpty() && this.namedArguments.isEmpty();
    }

    @Override
    public String toString() {
        return "ResourceFluentArguments: {\n" +
                "\t\t\tnamedArguments: \"" + this.namedArguments + "\"\n" +
                "\t\t\tpositionalArguments: \"" + this.positionalArguments + "\"\n" +
                "\t\t}";
    }
}
