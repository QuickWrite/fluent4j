package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;

import java.util.List;

/**
 * A resource containing a list of localization messages.
 * <br />
 * The different entries should all be of the same locale
 * as they aren't directly categorized in this resource.
 */
public interface FluentResource {
    /**
     * Returns a list of all entries that are being contained
     * in this resource.
     *
     * @return A list of all entries
     */
    FluentEntry[] entries();

    /**
     * Returns a single entry at the given position.
     *
     * @param index The index of the entry
     * @return A single entry
     */
    FluentEntry entry(final int index);
}
