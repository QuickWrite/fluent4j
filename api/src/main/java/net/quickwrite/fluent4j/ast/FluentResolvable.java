package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The base element for the construction of the message.
 *
 * @param <B> The type of ResultBuilder associated with the resolvable entity.
 */
public interface FluentResolvable<B extends ResultBuilder> {
    /**
     * @param scope The scope which contains the necessary information for this element.
     * @param builder The ResultBuilder used for resolving the entity.
     */
    void resolve(final FluentScope<B> scope, final B builder);
}
