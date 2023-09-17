package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;

/**
 * The FluentFunction interface represents a function.
 * Functions provide additional functionality available to the
 * localizers for formatting data or providing additional data.
 */
public interface FluentFunction {
    /**
     * Retrieves the identifier of the function.
     *
     * @return The identifier of the function
     */
    String getIdentifier();

    /**
     * Evaluates the function with the given scope and argument list.
     *
     * @param scope        The FluentScope used for evaluating
     * @param argumentList The ArgumentList containing the arguments passed to the function
     * @return A FluentPlaceable representing the evaluated function
     */
    FluentPlaceable parseFunction(final FluentScope scope, final ArgumentList argumentList);
}
