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
    private Set<FluentIdentifier<?>> traversed;

    private ArgumentList<B> argumentList;

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

    @Override
    public void setArguments(final ArgumentList<B> arguments) {
        this.argumentList = arguments;
    }

    public B builder() {
        return this.builder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FluentScope<B> clone() {
        try {
            final FluentResolverScope<B> newScope = (FluentResolverScope<B>) super.clone();

            newScope.traversed = new HashSet<>(traversed);

            return newScope;
        } catch (final CloneNotSupportedException ignored) {
            // This should NEVER happen

            // When it happens the exception will be thrown and when the application crashes
            // this message will be shown.
            // When this exception is being thrown something bad must have happened
            // which couldn't have been anticipated.
            throw new RuntimeException("For some reason the clone()-method of the FluentResolverScope class couldn't be" +
                    "executed and didn't clone the object. This isn't some behaviour which is intended and an issue" +
                    "should be created immediately.");
        }
    }
}
