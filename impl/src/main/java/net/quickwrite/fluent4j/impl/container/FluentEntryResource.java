package net.quickwrite.fluent4j.impl.container;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;

public record FluentEntryResource(FluentEntry[] entries) implements FluentResource {
    @Override
    public FluentEntry entry(int index) {
        return this.entries[index];
    }
}
