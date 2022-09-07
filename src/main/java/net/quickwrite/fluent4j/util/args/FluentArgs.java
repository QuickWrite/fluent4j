package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;

import java.util.*;

/**
 * A storage for the different arguments that
 * are used as the parameters when a message, term or a function
 * is getting accessed.
 */
public class FluentArgs {
    private final Map<String, FluentArgument> namedArguments;
    private final List<FluentArgument> positionalArguments;

    /**
     * An initialized object that has no values
     * so that it does not need to be created multiple
     * times and can be easily accessed.
     */
    public static final FluentArgs EMPTY_ARGS;

    static {
        EMPTY_ARGS = new FluentArgs();
    }

    /**
     * Creates a new empty argument
     * container.
     */
    public FluentArgs() {
        this(new HashMap<>(), new ArrayList<>());
    }

    /**
     * Creates a new argument container with the given
     * arguments.
     *
     * @param namedArguments The named arguments
     * @param positionalArguments The positional arguments
     */
    public FluentArgs(Map<String, FluentArgument> namedArguments, List<FluentArgument> positionalArguments) {
        this.namedArguments = namedArguments;
        this.positionalArguments = positionalArguments;
    }

    /**
     * Returns a named argument with the {@code key}.
     *
     * @param key The name of the argument
     * @return The value of the named argument
     */
    public FluentArgument getNamed(final String key) {
        return this.namedArguments.get(key);
    }

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
    public void sanitize(final FluentBundle bundle, final FluentArgs arguments) {
        for (String key : namedArguments.keySet()) {
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

    /**
     * Returns the {@link FluentArgument} with the given
     * position in the positional arguments.
     *
     * @param index The position the argument has
     * @return The argument itself
     */
    public FluentArgument getPositional(final int index) {
        return this.positionalArguments.get(index);
    }

    /**
     * Adds a new named argument to the named arguments.
     *
     * <p>
     * When this argument already exists it will be overwritten.
     *
     * @param key The name of the named argument
     * @param argument The argument value
     */
    public void setNamed(final String key, final FluentArgument argument) {
        this.namedArguments.put(key, argument);
    }

    /**
     * Adds a new positional argument at the end.
     *
     * @param argument The argument itself
     */
    public void addPositional(final FluentArgument argument) {
        this.positionalArguments.add(argument);
    }

    /**
     * Returns a named argument with the {@code key}.
     *
     * @param key The name of the argument
     * @return The value of the named argument
     */
    public FluentArgument get(String key) {
        return namedArguments.get(key);
    }

    @Override
    public String toString() {
        return "FluentArgumentList: {\n" +
                "\t\t\tnamedArguments: \"" + this.namedArguments + "\"\n" +
                "\t\t\tpositionalArguments: \"" + this.positionalArguments + "\"\n" +
                "\t\t}";
    }
}
