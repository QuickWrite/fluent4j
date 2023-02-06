package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;

import java.io.IOException;
import java.util.function.Function;

public class FluentFunctionReference extends ParameterizedLiteral<String> implements FluentSelect.Selectable {

    public FluentFunctionReference(final String identifier, final ArgumentList argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        unwrap(scope).resolve(scope, appendable);
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        // TODO: Better exceptions
        return scope.getBundle().getFunction(this.identifier).orElseThrow()
                .parseFunction(scope, this.argumentList);
    }

    @Override
    public Function<FluentSelect.FluentVariant, Boolean> selectChecker(final FluentScope scope) {
        // TODO: Better exceptions
        final FluentPlaceable placeable = scope.getBundle().getFunction(this.identifier).orElseThrow().parseFunction(scope, this.argumentList);

        if (placeable instanceof FluentSelect.Selectable) {
            return ((FluentSelect.Selectable) placeable).selectChecker(scope);
        }

        return new FluentCachedChecker(scope, placeable);
    }
}
