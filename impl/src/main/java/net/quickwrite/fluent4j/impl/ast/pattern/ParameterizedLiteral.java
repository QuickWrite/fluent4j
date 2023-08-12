package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;

public abstract class ParameterizedLiteral<I> implements FluentPlaceable {
    protected final I identifier;
    protected final ArgumentList argumentList;

    public ParameterizedLiteral(final I identifier, final ArgumentList argumentList) {
        this.identifier = identifier;
        this.argumentList = argumentList;
    }
}
