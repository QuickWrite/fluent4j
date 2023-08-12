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

public class FluentVariableReference implements FluentPlaceable, FluentSelect.Selectable {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        final FluentPattern pattern;
        try {
            pattern = unwrap(scope);
        } catch (final FluentPatternException exception) {
            exception.getDataWriter().write(builder);
            return;
        }

        pattern.resolve(scope, builder);
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) throws FluentPatternException {
        final ArgumentList.NamedArgument argument = scope.arguments().getArgument(identifier);

        if(argument == null) {
            throw ErrorUtil.getPlaceablePatternException(appender -> appender.append('$').append(identifier));
        }

        return argument;
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        try {
            return unwrap(scope).toSimpleString(scope);
        } catch (final FluentPatternException exception) {
            return "{$" + identifier + "}";
        }
    }

    @Override
    public SelectChecker selectChecker(final FluentScope scope) {
        final FluentPattern argument = scope.arguments().getArgument(identifier);

        if (argument == null) {
            return null;
        }

        if (argument instanceof FluentSelect.Selectable) {
            return ((FluentSelect.Selectable) argument).selectChecker(scope);
        }

        return new FluentCachedChecker(scope, argument);
    }
}
