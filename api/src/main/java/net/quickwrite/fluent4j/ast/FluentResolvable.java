package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The base element for the construction of the message.
 */
public interface FluentResolvable {
    /**
     * @param scope The scope which contains the necessary information for this element.
     * @param builder The ResultBuilder used for resolving the entity.
     */
    void resolve(final FluentScope scope, final ResultBuilder builder);
}
