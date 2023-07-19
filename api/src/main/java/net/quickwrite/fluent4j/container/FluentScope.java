package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Set;

/**
 * This interface is a basic container for the resolving process
 * of the messages which stores basic information that can be useful
 * for the different components.
 *
 * @param <B> The type of ResultBuilder associated with the resolvable entities.
 */
public interface FluentScope<B extends ResultBuilder> extends Cloneable {

    /**
     * Retrieves the FluentBundle associated with this FluentScope.
     *
     * @return The FluentBundle associated with this FluentScope
     */
    FluentBundle<B> bundle();

    /**
     * Retrieves the set of traversed FluentIdentifiers.
     *
     * @return The set of traversed FluentIdentifiers
     */
    Set<FluentIdentifier<?>> traversed();

    /**
     * Adds a FluentIdentifier to the set of traversed identifiers.
     *
     * @param key the FluentIdentifier to add
     * @return True if the FluentIdentifier was added successfully, false otherwise
     */
    boolean addTraversed(final FluentIdentifier<?> key);

    /**
     * Returns the Arguments that were provided with this scope.
     * <br />
     * So with the top scope the arguments are the code provided
     * arguments and with terms the arguments are the term provided
     * arguments.
     *
     * @return The ArgumentList
     */
    ArgumentList<B> arguments();

    /**
     * Sets the ArgumentList.
     *
     * @param arguments The ArgumentList to set
     */
    void setArguments(final ArgumentList<B> arguments);

    /**
     * Returns the builder that is being used to build the resolved
     * message itself.
     *
     * @return The ResultBuilder associated with this FluentScope
     */
    B builder();

    /**
     * Creates and returns a clone of this FluentScope.
     *
     * @return A clone of this FluentScope
     */
    FluentScope<B> clone();
}
