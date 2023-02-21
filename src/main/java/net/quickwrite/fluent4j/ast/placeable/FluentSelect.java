package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.function.Function;

public interface FluentSelect<B extends ResultBuilder> extends FluentPlaceable<B> {
    interface FluentVariant<B extends ResultBuilder> extends FluentResolvable<B> {
        FluentIdentifier<FluentVariantKey<B>> getIdentifier();

        interface FluentVariantKey<B extends ResultBuilder> extends FluentPattern<B> {
        }
    }

    interface Selectable<B extends ResultBuilder> {
        Function<FluentVariant<B>, Boolean> selectChecker(final FluentScope<B> scope);
    }
}
