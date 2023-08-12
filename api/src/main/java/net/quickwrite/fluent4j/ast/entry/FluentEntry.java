package net.quickwrite.fluent4j.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

/**
 * Represents a single entry that is being used
 * inside the parsed resource that holds the translation
 * content based upon an identifier.
 */
public interface FluentEntry extends FluentResolvable, FluentPattern {
    /**
     * Returns the identifier of this specific entry
     *
     * @return The identifier
     */
    FluentIdentifier<String> getIdentifier();
}
