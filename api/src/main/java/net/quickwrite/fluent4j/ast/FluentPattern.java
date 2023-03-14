package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentPattern<B extends ResultBuilder> extends FluentResolvable<B> {
    FluentPattern<B> unwrap(final FluentScope<B> scope);

    String toSimpleString(final FluentScope<B> scope);
}
