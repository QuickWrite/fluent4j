package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

import java.util.*;

/**
 * A storage for the different arguments that
 * are used as the parameters when a message, term or a function
 * is getting accessed.
 */
public class FunctionFluentArguments extends FluentArguments implements FunctionFluentArgs {
    protected final List<FluentElement> positionalArguments;

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
        super(namedArguments);
        this.positionalArguments = positionalArguments;
    }

    @Override
    public FluentArgs sanitize(final AccessorBundle bundle, int recursionDepth) {
        final FunctionFluentArgs args = new FunctionFluentArguments();

        for (final String key : namedArguments.keySet()) {
            final FluentElement argument = namedArguments.get(key);

            if (argument instanceof FluentArgumentResult) {
                args.setNamed(key, ((FluentArgumentResult) argument)
                        .getArgumentResult(bundle, recursionDepth - 1));
                continue;
            }

            args.setNamed(key, argument);
        }

        for (final FluentElement argument : positionalArguments) {
            if (argument instanceof FluentArgumentResult) {
                args.addPositional(((FluentArgumentResult) argument)
                        .getArgumentResult(bundle, recursionDepth - 1));
                continue;
            }
            args.addPositional(argument);
        }

        return args;
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
    public void addPositional(final FluentElement argument) {
        this.positionalArguments.add(argument);
    }

    @Override
    public boolean isEmpty() {
        return this.positionalArguments.isEmpty() && this.namedArguments.isEmpty();
    }

    @Override
    public String toString() {
        return "FunctionFluentArguments: {\n" +
                "\t\t\tnamedArguments: \"" + this.namedArguments + "\"\n" +
                "\t\t\tpositionalArguments: \"" + this.positionalArguments + "\"\n" +
                "\t\t}";
    }
}
