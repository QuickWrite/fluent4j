package net.quickwrite.fluent4j.impl.ast.pattern.container.cache;

import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

public class FluentCachedChecker<B extends ResultBuilder> implements Function<FluentSelect.FluentVariant<B>, Boolean> {
    private final Map.Entry<FluentScope<B>, String> resolveCache;
    private final FluentScope<B> scope;

    public FluentCachedChecker(final FluentScope<B> scope, final FluentResolvable<B> argument) {
        final B builder = scope.builder().getSimpleBuilder();

        argument.resolve(scope, builder);

        this.resolveCache = new AbstractMap.SimpleImmutableEntry<>(scope, builder.toString());
        this.scope = scope;
    }

    @Override
    public Boolean apply(final FluentSelect.FluentVariant<B> variant) {
        return this.resolveCache.getValue().equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope));
    }
}