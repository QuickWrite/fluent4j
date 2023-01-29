package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;

import java.util.List;

public abstract class FluentEntryBase implements FluentEntry {
    private final String identifier;
    private final List<FluentPattern> patterns;

    public FluentEntryBase(final String identifier, final List<FluentPattern> patterns) {
        this.identifier = identifier;
        this.patterns = patterns;
    }
}
