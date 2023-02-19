package net.quickwrite.fluent4j.impl.container;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;

public class FluentEntryResource<B extends ResultBuilder> implements FluentResource<B> {
    private final List<FluentEntry<B>> entries;

    public FluentEntryResource(final List<FluentEntry<B>> entries) {
        this.entries = entries;
    }

    @Override
    public List<FluentEntry<B>> entries() {
        return this.entries;
    }

    @Override
    public FluentEntry<B> entry(int index) {
        return this.entries.get(index);
    }
}
