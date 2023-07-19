package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;

/**
 * A resource containing a list of localization messages.
 * <br />
 * The different entries should all be of the same locale
 * as they aren't directly categorized in this resource.
 *
 * @param <B> The type of ResultBuilder associated with the resolvable entities.
 */
public interface FluentResource<B extends ResultBuilder> {
    /**
     * Returns a list of all entries that are being contained
     * in this resource.
     *
     * @return A list of all entries
     */
    List<FluentEntry<B>> entries();

    /**
     * Returns a single entry at the given position.
     *
     * @param index The index of the entry
     * @return A single entry
     */
    FluentEntry<B> entry(final int index);
}
