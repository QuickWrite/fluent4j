package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.container.exception.FluentPatternException;
import net.quickwrite.fluent4j.container.exception.FluentSelectException;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.function.Function;

public class FluentFunctionReference<B extends ResultBuilder> extends ParameterizedLiteral<String, B> implements FluentSelect.Selectable<B> {

    public FluentFunctionReference(final String identifier, final ArgumentList<B> argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        unwrap(scope).resolve(scope, builder);
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return scope.bundle().getFunction(this.identifier)
                .orElseThrow(() -> FluentPatternException.getPlaceable(writer -> writer.append(this.identifier)))
                .parseFunction(scope, this.argumentList);
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return unwrap(scope).toSimpleString(scope);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<FluentSelect.FluentVariant<B>, Boolean> selectChecker(final FluentScope<B> scope) throws FluentSelectException {
        final FluentPlaceable<B> placeable = scope.bundle().getFunction(this.identifier)
                .orElseThrow(FluentSelectException::new)
                .parseFunction(scope, this.argumentList);

        if (placeable instanceof FluentSelect.Selectable) {
            return ((FluentSelect.Selectable) placeable).selectChecker(scope);
        }

        return new FluentCachedChecker<>(scope, placeable);
    }
}
