package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;
import net.quickwrite.fluent4j.impl.util.ErrorUtil;
import net.quickwrite.fluent4j.result.ResultBuilder;

public class FluentVariableReference<B extends ResultBuilder> implements FluentPlaceable<B>, FluentSelect.Selectable<B> {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        final FluentPattern<B> pattern;
        try {
            pattern = unwrap(scope);
        } catch (final FluentPatternException exception) {
            exception.getDataWriter().write(builder);
            return;
        }

        pattern.resolve(scope, builder);
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) throws FluentPatternException {
        final ArgumentList.NamedArgument<B> argument = scope.arguments().getArgument(identifier);

        if(argument == null) {
            throw ErrorUtil.getPlaceablePatternException(appender -> appender.append('$').append(identifier));
        }

        return argument;
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        try {
            return unwrap(scope).toSimpleString(scope);
        } catch (final FluentPatternException exception) {
            return "{$" + identifier + "}";
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public SelectChecker<B> selectChecker(final FluentScope<B> scope) {
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
