package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentFunction extends ParameterizedLiteral<String> implements FluentSelect.Selectable {

    public FluentFunction(final String identifier, final ArgumentList attributes) {
        super(identifier, attributes);
    }
}
