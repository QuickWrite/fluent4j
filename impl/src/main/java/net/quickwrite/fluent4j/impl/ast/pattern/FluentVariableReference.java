package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.function.Function;

public class FluentVariableReference<B extends ResultBuilder> implements FluentPlaceable<B>, FluentSelect.Selectable<B> {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        final FluentPattern<B> pattern = unwrap(scope);
        if (pattern == null) {
            throw FluentPatternException.getPlaceable(appender -> appender.append('$').append(identifier));
        }

        pattern.resolve(scope, builder);
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return scope.arguments().getArgument(identifier);
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return unwrap(scope).toSimpleString(scope);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<FluentSelect.FluentVariant<B>, Boolean> selectChecker(final FluentScope<B> scope) {
        final FluentPattern<B> argument = scope.arguments().getArgument(identifier);

        if (argument == null) {
            return null;
        }

        if (argument instanceof FluentSelect.Selectable) {
            return ((FluentSelect.Selectable<B>) argument).selectChecker(scope);
        }

        return new FluentCachedChecker<>(scope, argument);
    }
}
