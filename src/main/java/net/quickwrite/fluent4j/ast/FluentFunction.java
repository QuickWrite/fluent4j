package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentFunction<B extends ResultBuilder> {
    String getIdentifier();

    FluentPlaceable<B> parseFunction(final FluentScope<B> scope, final ArgumentList<B> argumentList);
}
