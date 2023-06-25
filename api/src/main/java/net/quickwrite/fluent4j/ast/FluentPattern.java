package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentPattern<B extends ResultBuilder> extends FluentResolvable<B> {
    FluentPattern<B> unwrap(final FluentScope<B> scope) throws FluentPatternException;

    String toSimpleString(final FluentScope<B> scope);
}
