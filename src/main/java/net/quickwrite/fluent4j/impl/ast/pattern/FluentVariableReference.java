package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;

public class FluentVariableReference implements FluentPattern {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }
}
