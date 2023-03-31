package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.result.ResultBuilder;

public abstract class ParameterizedLiteral<I, B extends ResultBuilder> implements FluentPlaceable<B> {
    protected final I identifier;
    protected final ArgumentList<B> argumentList;

    public ParameterizedLiteral(final I identifier, final ArgumentList<B> argumentList) {
        this.identifier = identifier;
        this.argumentList = argumentList;
    }
}
