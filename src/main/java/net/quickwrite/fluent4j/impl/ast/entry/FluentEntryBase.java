package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;

import java.util.List;

public abstract class FluentEntryBase extends FluentBaseElement implements FluentEntry {
    protected final List<FluentEntry.Attribute> attributes;

    public FluentEntryBase(final String identifier, final List<FluentPattern> patterns, final List<FluentEntry.Attribute> attributes) {
        super(identifier, patterns);

        this.attributes = attributes;
    }
}
