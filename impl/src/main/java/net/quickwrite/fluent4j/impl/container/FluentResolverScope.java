package net.quickwrite.fluent4j.impl.container;

import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.HashSet;
import java.util.Set;

public class FluentResolverScope implements FluentScope {
    private final FluentBundle bundle;
    private Set<FluentIdentifier<?>> traversed;

    private ArgumentList argumentList;

    private final ResultBuilder builder;

    public FluentResolverScope(final FluentBundle bundle, final ArgumentList argumentList, final ResultBuilder builder) {
        this(bundle, argumentList, new HashSet<>(), builder);
    }

    public FluentResolverScope(final FluentBundle bundle,
                               final ArgumentList argumentList,
                               final Set<FluentIdentifier<?>> traversed,
                               final ResultBuilder builder
    ) {
        this.bundle = bundle;
        this.traversed = traversed;
        this.argumentList = argumentList;
        this.builder = builder;
    }

    @Override
    public FluentBundle bundle() {
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
    public ArgumentList arguments() {
        return this.argumentList;
    }

    @Override
    public void setArguments(final ArgumentList arguments) {
        this.argumentList = arguments;
    }

    public ResultBuilder builder() {
        return this.builder;
    }

    @Override
    public FluentScope clone() {
        try {
            final FluentResolverScope newScope = (FluentResolverScope) super.clone();

            newScope.traversed = new HashSet<>(traversed);

            return newScope;
        } catch (final CloneNotSupportedException ignored) {
            // This should NEVER happen

            // If it happens the exception will be thrown and when the application crashes
            // this message will be shown.
            // When this exception is being thrown something bad must have happened
            // which couldn't have been anticipated.
            throw new RuntimeException("For some reason the clone()-method of the FluentResolverScope class couldn't be" +
                    "executed and didn't clone the object. This isn't some behaviour which is intended and an issue" +
                    "should be created immediately.");
        }
    }
}
