package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentEntry;

public abstract class FluentEntryBase implements FluentEntry {
    private final String identifier;

    public FluentEntryBase(final String identifier) {
        this.identifier = identifier;
    }
}
