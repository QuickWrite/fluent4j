package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

import java.util.List;

public abstract class FluentEntryBase extends FluentBaseElement<String> implements FluentEntry {
    protected final List<FluentEntry.Attribute> attributes;

    public FluentEntryBase(final FluentIdentifier<String> identifier, final List<FluentPattern> patterns, final List<FluentEntry.Attribute> attributes) {
        super(identifier, patterns);

        this.attributes = attributes;
    }

    @Override
    public FluentIdentifier<String> getIdentifier() {
        return this.identifier;
    }

    @Override
    public List<Attribute> getAttributes() {
        return this.attributes;
    }
}
