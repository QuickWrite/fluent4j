package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentNumberLiteral implements FluentPlaceable, AttributeList.NamedAttribute, FluentSelect.Selectable {
    // TODO: Better number storing, formatting etc.
    private final String number;

    public FluentNumberLiteral(final String number) {
        this.number = number;
    }

    @Override
    }
}
