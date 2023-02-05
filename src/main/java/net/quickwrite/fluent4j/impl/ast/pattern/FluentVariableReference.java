package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

public class FluentVariableReference implements FluentPlaceable, FluentSelect.Selectable {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        scope.getArguments().getArgument(identifier).resolve(scope, appendable);
    }

    private Map.Entry<FluentScope, String> resolveCache = null;

    @Override
    public synchronized boolean selectCheck(final FluentScope scope, final FluentSelect.FluentVariant variant) {
        final FluentPattern argument = scope.getArguments().getArgument(identifier);

        if (argument instanceof FluentSelect.Selectable) {
            return ((FluentSelect.Selectable) argument).selectCheck(scope, variant);
        }

        if (resolveCache != null && resolveCache.getKey() == scope) {
            return resolveCache.getValue().equals(variant.getIdentifier().getSimpleIdentifier());
        }

        final StringBuilder builder = new StringBuilder();
        try {
            argument.resolve(scope, builder);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        this.resolveCache = new AbstractMap.SimpleImmutableEntry<>(scope, builder.toString());

        return this.resolveCache.getValue().equals(variant.getIdentifier().getSimpleIdentifier());
    }

    @Override
    public void endSelect() {
        this.resolveCache = null;
    }
}
