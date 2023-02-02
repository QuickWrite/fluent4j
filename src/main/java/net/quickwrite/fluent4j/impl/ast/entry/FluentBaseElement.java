package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;

import java.util.List;

public abstract class FluentBaseElement {
    protected final String identifier;
    protected final List<FluentPattern> patterns;

    protected FluentBaseElement(final String identifier, final List<FluentPattern> patterns) {
        this.identifier = identifier;
        this.patterns = patterns;
    }
}
