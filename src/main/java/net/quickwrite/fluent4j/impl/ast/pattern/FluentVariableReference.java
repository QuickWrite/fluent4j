package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;

public class FluentVariableReference implements FluentPlaceable {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean canSelect() {
        return true;
    }
}
