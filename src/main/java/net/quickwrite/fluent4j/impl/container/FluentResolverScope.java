package net.quickwrite.fluent4j.impl.container;

import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.HashSet;
import java.util.Set;

public class FluentResolverScope<B extends ResultBuilder> implements FluentScope<B> {
    private final FluentBundle<B> bundle;
    private final Set<FluentIdentifier<?>> traversed;

    private final ArgumentList<B> argumentList;

    private final B builder;

    public FluentResolverScope(final FluentBundle<B> bundle, final ArgumentList<B> argumentList, final B builder) {
        this(bundle, argumentList, new HashSet<>(), builder);
    }

    public FluentResolverScope(final FluentBundle<B> bundle,
                               final ArgumentList<B> argumentList,
                               final Set<FluentIdentifier<?>> traversed,
                               final B builder
    ) {
        this.bundle = bundle;
        this.traversed = traversed;
        this.argumentList = argumentList;
        this.builder = builder;
    }

    @Override
    public FluentBundle<B> bundle() {
        return this.bundle;
    }

    @Override
    public Set<FluentIdentifier<?>> traversed() {
        return this.traversed;
    }

    @Override
    public boolean addTraversed(final FluentIdentifier<?> key) {
        return this.traversed.add(key);
    }

    @Override
    public ArgumentList<B> arguments() {
        return this.argumentList;
    }

    public B builder() {
        return this.builder;
    }
}
