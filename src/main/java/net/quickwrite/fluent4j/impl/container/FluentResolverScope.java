package net.quickwrite.fluent4j.impl.container;

import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentScope;

import java.util.HashSet;
import java.util.Set;

public class FluentResolverScope implements FluentScope {
    private final FluentBundle bundle;
    private final Set<FluentIdentifier> traversed;

    private final ArgumentList argumentList;

    public FluentResolverScope(final FluentBundle bundle, final ArgumentList argumentList) {
        this(bundle, argumentList, new HashSet<>());
    }

    public FluentResolverScope(final FluentBundle bundle, final ArgumentList argumentList, final Set<FluentIdentifier> traversed) {
        this.bundle = bundle;
        this.traversed = traversed;
        this.argumentList = argumentList;
    }

    @Override
    public FluentBundle getBundle() {
        return this.bundle;
    }

    @Override
    public Set<FluentIdentifier> getTraversed() {
        return this.traversed;
    }

    @Override
    public boolean addTraversed(final FluentIdentifier key) {
        return this.traversed.add(key);
    }

    @Override
    public ArgumentList getArguments() {
        return this.argumentList;
    }
}
