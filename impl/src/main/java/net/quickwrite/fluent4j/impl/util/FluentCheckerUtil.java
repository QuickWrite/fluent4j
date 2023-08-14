package net.quickwrite.fluent4j.impl.util;

import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class FluentCheckerUtil {
    private FluentCheckerUtil() {}

    public static FluentSelect.FluentVariant check(final FluentScope scope,
                                                   final FluentResolvable argument,
                                                   final FluentSelect.FluentVariant[] variants,
                                                   final FluentSelect.FluentVariant defaultVariant
    ) {
        final ResultBuilder builder = scope.builder().getSimpleBuilder();

        argument.resolve(scope, builder);

        final String result = builder.toString();

        for (final FluentSelect.FluentVariant variant : variants) {
            if (result.equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope))) {
                return variant;
            }
        }

        return defaultVariant;
    }
}
