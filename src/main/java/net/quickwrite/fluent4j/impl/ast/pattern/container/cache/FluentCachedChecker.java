package net.quickwrite.fluent4j.impl.ast.pattern.container.cache;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

public class FluentCachedChecker implements Function<FluentSelect.FluentVariant, Boolean> {
    private final Map.Entry<FluentScope, String> resolveCache;

    public FluentCachedChecker(final FluentScope scope, final FluentResolvable argument) {
        final StringBuilder builder = new StringBuilder();
        try {
            argument.resolve(scope, builder);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        this.resolveCache = new AbstractMap.SimpleImmutableEntry<>(scope, builder.toString());
    }

    @Override
    public Boolean apply(final FluentSelect.FluentVariant variant) {
        return this.resolveCache.getValue().equals(variant.getIdentifier().getSimpleIdentifier());
    }
}
