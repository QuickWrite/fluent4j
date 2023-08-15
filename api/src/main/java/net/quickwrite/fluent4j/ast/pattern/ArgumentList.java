package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The ArgumentList interface represents a list of arguments used in Fluent4J patterns.
 * ArgumentList provides methods to access and retrieve named and positional arguments.
 */
public interface ArgumentList {
    /**
     * Retrieves the named argument with the specified name from the ArgumentList.
     *
     * @param name The name of the named argument
     * @return The NamedArgument associated with the specified name
     */
    Optional<NamedArgument> getArgument(final String name);

    /**
     * Retrieves the positional argument at the specified index from the ArgumentList.
     *
     * @param index The index of the positional argument
     * @return The FluentPattern representing the positional argument
     */
    Optional<FluentPattern> getArgument(final int index);

    /**
     * Creates an empty ArgumentList instance.
     *
     * @return An empty ArgumentList
     */
    static ArgumentList empty() {
        return EmptyArgumentList.SELF;
    }

    /**
     * The Builder interface provides methods for building an ArgumentList.
     * This interface extends the Builder pattern and acts as a fluent interface to add various types of arguments.
     */
    interface Builder extends net.quickwrite.fluent4j.util.Builder<ArgumentList> {
        /**
         * Adds a named argument to the ArgumentList with the specified name.
         *
         * @param name     The name of the named argument
         * @param argument The NamedArgument to be added
         * @return The Builder instance for further building
         */
        Builder add(final String name, final NamedArgument argument);

        /**
         * Adds a named argument with a BigDecimal value to the ArgumentList.
         *
         * @param name   The name of the named argument
         * @param number The BigDecimal value to be added
         * @return the Builder instance for further building
         */
        Builder add(final String name, final BigDecimal number);

        /**
         * Adds a named argument with a long value to the ArgumentList.
         *
         * @param name   The name of the named argument
         * @param number The long value to be added
         * @return the Builder instance for further building
         */
        Builder add(final String name, final long number);

        /**
         * Adds a named argument with a double value to the ArgumentList.
         *
         * @param name   The name of the named argument
         * @param number The double value to be added
         * @return the Builder instance for further building
         */
        Builder add(final String name, final double number);

        /**
         * Adds a named argument with a String input to the ArgumentList.
         *
         * @param name  The name of the named argument
         * @param input The String input to be added
         * @return the Builder instance for further building
         */
        Builder add(final String name, final String input);
    }

    /**
     * The PlenaryBuilder interface extends the Builder interface and provides additional methods for building an ArgumentList.
     * This interface acts as a fluent interface to add positional arguments.
     */
    interface PlenaryBuilder extends Builder {

        /**
         * Adds a positional argument represented by a FluentPattern to the ArgumentList.
         *
         * @param argument The FluentPattern representing the positional argument
         * @return The Builder instance for further building
         */
        Builder add(final FluentPattern argument);

        /**
         * Adds a positional argument with a BigDecimal value to the ArgumentList.
         *
         * @param number The BigDecimal value to be added
         * @return The Builder instance for further building
         */
        Builder add(final BigDecimal number);

        /**
         * Adds a positional argument with a long value to the ArgumentList.
         *
         * @param number The long value to be added
         * @return The Builder instance for further building
         */
        Builder add(final long number);

        /**
         * Adds a positional argument with a double value to the ArgumentList.
         *
         * @param number The double value to be added
         * @return The Builder instance for further building
         */
        Builder add(final double number);

        /**
         * Adds a positional argument with a String input to the ArgumentList.
         *
         * @param input The String input to be added
         * @return The Builder instance for further building
         */
        Builder add(final String input);
    }

    /**
     * The NamedArgument interface represents a named argument used in Fluent4J patterns.
     * NamedArgument extends FluentPattern and can be used in place of a FluentPattern in an ArgumentList.
     */
    interface NamedArgument extends FluentPattern {

    }

    /**
     * The EmptyArgumentList class represents an immutable empty ArgumentList.
     * It is used to provide an empty ArgumentList instance through the static empty() method in ArgumentList interface.
     */
    class EmptyArgumentList implements ArgumentList {
        /**
         * An instance of ImmutableArgumentList to represent an empty ArgumentList.
         */
        public static final EmptyArgumentList SELF = new EmptyArgumentList();

        private EmptyArgumentList() {}

        @Override
        public Optional<NamedArgument> getArgument(final String name) {
            return Optional.empty();
        }

        @Override
        public Optional<FluentPattern> getArgument(final int index) {
            return Optional.empty();
        }
    }
}
