package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentFunction extends ParameterizedLiteral<String> implements FluentSelect.Selectable {

    public FluentFunction(final String identifier, final AttributeList attributes) {
        super(identifier, attributes);
    }
}
