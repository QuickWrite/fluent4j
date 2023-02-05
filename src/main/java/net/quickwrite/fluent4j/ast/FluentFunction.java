package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;

public interface FluentFunction {
    String getIdentifier();

    FluentPlaceable parseFunction(final FluentScope scope, final ArgumentList argumentList);
}
