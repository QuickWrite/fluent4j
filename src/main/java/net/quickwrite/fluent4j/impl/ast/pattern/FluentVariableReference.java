package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentVariableReference implements FluentPlaceable, FluentSelect.Selectable {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }
}
