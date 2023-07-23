package net.quickwrite.fluent4j.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * Represents a single entry that is being used
 * inside the parsed resource that holds the translation
 * content based upon an identifier.
 *
 * @param <B> The type of ResultBuilder used by the FluentEntry
 */
public interface FluentEntry<B extends ResultBuilder> extends FluentResolvable<B>, FluentPattern<B> {
    /**
     * Returns the identifier of this specific entry
     *
     * @return The identifier
     */
    FluentIdentifier<String> getIdentifier();
}
