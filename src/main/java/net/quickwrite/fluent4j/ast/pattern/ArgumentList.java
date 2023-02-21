package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.pattern.container.FluentArgumentContainer;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface ArgumentList<B extends ResultBuilder> {
    ArgumentList<? extends ResultBuilder> EMPTY = new FluentArgumentContainer<>();

    NamedArgument<B> getArgument(final String name);
    FluentPattern<B> getArgument(final int index);

    interface NamedArgument<B extends ResultBuilder> extends FluentPattern<B> {

    }
}
