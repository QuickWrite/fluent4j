package net.quickwrite.fluent4j.impl.container;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;

import java.util.List;

public class FluentEntryResource implements FluentResource {
    private final FluentEntry[] entries;

    public FluentEntryResource(final FluentEntry[] entries) {
        this.entries = entries;
    }

    @Override
    public FluentEntry[] entries() {
        return this.entries;
    }

    @Override
    public FluentEntry entry(int index) {
        return this.entries[index];
    }
}
