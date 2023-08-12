package net.quickwrite.fluent4j.impl.ast.pattern.container.cache;

import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.AbstractMap;
import java.util.Map;

public class FluentCachedChecker implements FluentSelect.Selectable.SelectChecker {
    private final Map.Entry<FluentScope, String> resolveCache;
    private final FluentScope scope;

    public FluentCachedChecker(final FluentScope scope, final FluentResolvable argument) {
        final ResultBuilder builder = scope.builder().getSimpleBuilder();

        argument.resolve(scope, builder);

        this.resolveCache = new AbstractMap.SimpleImmutableEntry<>(scope, builder.toString());
        this.scope = scope;
    }

    @Override
    public boolean check(final FluentSelect.FluentVariant variant) {
        return this.resolveCache.getValue().equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope));
    }
}
