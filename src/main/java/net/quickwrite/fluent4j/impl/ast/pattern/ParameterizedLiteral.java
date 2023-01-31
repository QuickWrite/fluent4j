package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;

public abstract class ParameterizedLiteral<I> implements FluentPlaceable {
    protected final I identifier;
    protected final AttributeList attributes;

    public ParameterizedLiteral(final I identifier, final AttributeList attributes) {
        this.identifier = identifier;
        this.attributes = attributes;
    }
}
