package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;
import net.quickwrite.fluent4j.impl.util.ErrorUtil;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;
import java.util.function.Function;

public class FluentFunctionReference<B extends ResultBuilder> extends ParameterizedLiteral<String, B> implements FluentSelect.Selectable<B> {

    public FluentFunctionReference(final String identifier, final ArgumentList<B> argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        try {
            unwrap(scope).resolve(scope, builder);
        } catch (final FluentPatternException exception) {
            exception.getDataWriter().write(builder);
        }
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) throws FluentPatternException {
        return scope.bundle().getFunction(this.identifier)
                .orElseThrow(() -> ErrorUtil.getPlaceablePatternException(builder -> builder.append(this.identifier).append("()")))
                .parseFunction(scope, this.argumentList);
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        try {
            return unwrap(scope).toSimpleString(scope);
        } catch (final FluentPatternException exception) {
            return "{" + identifier + "()}";
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<FluentSelect.FluentVariant<B>, Boolean> selectChecker(final FluentScope<B> scope) {
        final Optional<FluentFunction<B>> function = scope.bundle().getFunction(this.identifier);

        if (function.isEmpty()) {
            return null;
        }

        final FluentPlaceable<B> placeable = function.get().parseFunction(scope, this.argumentList);

        if (placeable instanceof FluentSelect.Selectable) {
            return ((FluentSelect.Selectable) placeable).selectChecker(scope);
        }

        return new FluentCachedChecker<>(scope, placeable);
    }
}
