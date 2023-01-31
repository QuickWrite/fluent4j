package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;

public class FluentNumberLiteral implements FluentPlaceable, AttributeList.NamedAttribute {
    // TODO: Better number storing, formatting etc.
    private final String number;

    public FluentNumberLiteral(final String number) {
        this.number = number;
    }

    @Override
    public boolean canSelect() {
        return true;
    }
}
