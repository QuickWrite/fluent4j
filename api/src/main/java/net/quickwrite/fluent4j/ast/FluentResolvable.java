package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentResolvable<B extends ResultBuilder> {
    void resolve(final FluentScope<B> scope, final B builder);
}
