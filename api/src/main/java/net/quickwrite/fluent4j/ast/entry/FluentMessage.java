package net.quickwrite.fluent4j.ast.entry;

import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * Represents a FluentMessage specifically
 *
 * @param <B> The type of ResultBuilder used by the FluentMessage
 */
public interface FluentMessage<B extends ResultBuilder> extends FluentAttributeEntry<B> {
}
