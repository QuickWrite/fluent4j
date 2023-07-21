package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The FluentFunction interface represents a function.
 * Functions provide additional functionality available to the
 * localizers for formatting data or providing additional data.
 *
 * @param <B> The type of ResultBuilder used by the FluentFunction
 */
public interface FluentFunction<B extends ResultBuilder> {
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
    FluentPlaceable<B> parseFunction(final FluentScope<B> scope, final ArgumentList<B> argumentList);
}
