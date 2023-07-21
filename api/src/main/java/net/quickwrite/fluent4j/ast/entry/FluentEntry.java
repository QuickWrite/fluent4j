package net.quickwrite.fluent4j.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.Optional;

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

    /**
     * Returns a list of attributes that this entry has.
     *
     * <p>
     *     If there are no entries this should return
     *     an empty list.
     * </p>
     *
     * @return A list of attributes
     */
    List<FluentEntry.Attribute<B>> getAttributes();

    /**
     * Returns the specific attribute with the given
     * identifier.
     * <br />
     * If the attribute doesn't exist it will return
     * an empty optional.
     *
     * <p>
     *     This search for the attribute is a linear search
     *     with the default implementation and with that has
     *     a time complexity of {@code O(n)}.
     * </p>
     *
     * @param identifier The identifier of the attribute
     * @return The attribute
     */
    default Optional<FluentEntry.Attribute<B>> getAttribute(final String identifier) {
        for (final FluentEntry.Attribute<B> attribute : getAttributes()) {
            if(attribute.getIdentifier().getSimpleIdentifier().equals(identifier)) {
                return Optional.of(attribute);
            }
        }

        return Optional.empty();
    }

    /**
     * A single attribute of the FluentEntry.
     *
     * @param <B> The type of ResultBuilder used by the Attribute
     */
    interface Attribute<B extends ResultBuilder> extends FluentResolvable<B> {
        /**
         * Returns the identifier of the attribute
         *
         * @return The identifier
         */
        FluentIdentifier<String> getIdentifier();

        /**
         * Returns a list of patterns that are being stored inside
         * the Attribute.
         *
         * @return A list of patterns
         */
        List<FluentPattern<B>> getPatterns();
    }
}
