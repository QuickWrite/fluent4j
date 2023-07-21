package net.quickwrite.fluent4j.ast.identifier;

/**
 * The FluentIdentifier interface represents an identifier.
 * Identifiers are used to represent names or keys within the localization messages.
 *
 * @param <I> The type of the identifier
 */
public interface FluentIdentifier<I> {
    /**
     * Retrieves the simple form of the identifier.
     * The simple form typically represents the short name or key.
     *
     * @return The simple form of the identifier
     */
    I getSimpleIdentifier();

    /**
     * Retrieves the full form of the identifier.
     * The full form may represent the complete identifier with any additional context or namespace.
     *
     * @return The full form of the identifier
     */
    I getFullIdentifier();

    /**
     * Calculates the hash code of the identifier.
     *
     * @return The hash code of the identifier
     */
    int hashCode();

    /**
     * Compares this identifier to the specified object for equality.
     * Two identifiers are considered equal if their simple forms are equal.
     *
     * @param o The object to compare with
     * @return true if the specified object is equal to this identifier, false otherwise
     */
    boolean equals(final Object o);
}