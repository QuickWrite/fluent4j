package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.math.BigDecimal;

/**
 * The ArgumentList interface represents a list of arguments used in Fluent4J patterns.
 * ArgumentList provides methods to access and retrieve named and positional arguments.
 *
 * @param <B> The type of ResultBuilder used by the arguments
 */
public interface ArgumentList<B extends ResultBuilder> {
    /**
     * Retrieves the named argument with the specified name from the ArgumentList.
     *
     * @param name The name of the named argument
     * @return The NamedArgument associated with the specified name, or null if not found
     */
    NamedArgument<B> getArgument(final String name);

    /**
     * Retrieves the positional argument at the specified index from the ArgumentList.
     *
     * @param index The index of the positional argument
     * @return The FluentPattern representing the positional argument, or null if not found
     */
    FluentPattern<B> getArgument(final int index);

    /**
     * Creates an empty ArgumentList instance.
     *
     * @param <B> The type of ResultBuilder used by the ArgumentList
     * @return An empty ArgumentList
     */
    @SuppressWarnings("unchecked")
    static <B extends ResultBuilder> ArgumentList<B> empty() {
        return (ArgumentList<B>) ImmutableArgumentList.SELF;
    }

    /**
     * The Builder interface provides methods for building an ArgumentList.
     * This interface extends the Builder pattern and acts as a fluent interface to add various types of arguments.
     *
     * @param <B> The type of ResultBuilder used by the Builder
     */
    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<ArgumentList<B>> {
        /**
         * Adds a named argument to the ArgumentList with the specified name.
         *
         * @param name     The name of the named argument
         * @param argument The NamedArgument to be added
         * @return The Builder instance for further building
         */
        Builder<B> add(final String name, final NamedArgument<B> argument);

        /**
         * Adds a named argument with a BigDecimal value to the ArgumentList.
         *
         * @param name   The name of the named argument
         * @param number The BigDecimal value to be added
         * @return the Builder instance for further building
         */
        Builder<B> add(final String name, final BigDecimal number);

        /**
         * Adds a named argument with a long value to the ArgumentList.
         *
         * @param name   The name of the named argument
         * @param number The long value to be added
         * @return the Builder instance for further building
         */
        Builder<B> add(final String name, final long number);

        /**
         * Adds a named argument with a double value to the ArgumentList.
         *
         * @param name   The name of the named argument
         * @param number The double value to be added
         * @return the Builder instance for further building
         */
        Builder<B> add(final String name, final double number);

        /**
         * Adds a named argument with a String input to the ArgumentList.
         *
         * @param name  The name of the named argument
         * @param input The String input to be added
         * @return the Builder instance for further building
         */
        Builder<B> add(final String name, final String input);
    }

    /**
     * The PlenaryBuilder interface extends the Builder interface and provides additional methods for building an ArgumentList.
     * This interface acts as a fluent interface to add positional arguments.
     *
     * @param <B> The type of ResultBuilder used by the PlenaryBuilder
     */
    interface PlenaryBuilder<B extends ResultBuilder> extends Builder<B> {

        /**
         * Adds a positional argument represented by a FluentPattern to the ArgumentList.
         *
         * @param argument The FluentPattern representing the positional argument
         * @return The Builder instance for further building
         */
        Builder<B> add(final FluentPattern<B> argument);

        /**
         * Adds a positional argument with a BigDecimal value to the ArgumentList.
         *
         * @param number The BigDecimal value to be added
         * @return The Builder instance for further building
         */
        Builder<B> add(final BigDecimal number);

        /**
         * Adds a positional argument with a long value to the ArgumentList.
         *
         * @param number The long value to be added
         * @return The Builder instance for further building
         */
        Builder<B> add(final long number);

        /**
         * Adds a positional argument with a double value to the ArgumentList.
         *
         * @param number The double value to be added
         * @return The Builder instance for further building
         */
        Builder<B> add(final double number);

        /**
         * Adds a positional argument with a String input to the ArgumentList.
         *
         * @param input The String input to be added
         * @return The Builder instance for further building
         */
        Builder<B> add(final String input);
    }

    /**
     * The NamedArgument interface represents a named argument used in Fluent4J patterns.
     * NamedArgument extends FluentPattern and can be used in place of a FluentPattern in an ArgumentList.
     *
     * @param <B> The type of ResultBuilder used by the NamedArgument
     */
    interface NamedArgument<B extends ResultBuilder> extends FluentPattern<B> {

    }
}

/**
 * The ImmutableArgumentList class represents an immutable empty ArgumentList.
 * It is used to provide an empty ArgumentList instance through the static empty() method in ArgumentList interface.
 *
 * @param <B> The type of ResultBuilder used by the ImmutableArgumentList
 */
class ImmutableArgumentList<B extends ResultBuilder> implements ArgumentList<B> {
    /**
     * An instance of ImmutableArgumentList to represent an empty ArgumentList.
     */
    public static final ImmutableArgumentList<? extends ResultBuilder> SELF = new ImmutableArgumentList<>();

    @Override
    public NamedArgument<B> getArgument(final String name) {
        return null;
    }

    @Override
    public FluentPattern<B> getArgument(final int index) {
        return null;
    }
}
