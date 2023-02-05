package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;

import java.io.IOException;
import java.util.function.Function;

public class FluentVariableReference implements FluentPlaceable, FluentSelect.Selectable {
    private final String identifier;

    public FluentVariableReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        scope.getArguments().getArgument(identifier).resolve(scope, appendable);
    }

    @Override
    public Function<FluentSelect.FluentVariant, Boolean> selectChecker(final FluentScope scope) {
        final FluentPattern argument = scope.getArguments().getArgument(identifier);

        if (argument instanceof FluentSelect.Selectable) {
            return ((FluentSelect.Selectable) argument).selectChecker(scope);
        }

        return new FluentCachedChecker(scope, argument);
    }
}
