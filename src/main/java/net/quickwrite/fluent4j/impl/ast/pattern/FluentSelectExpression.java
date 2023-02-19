package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.container.exception.FluentSelectException;
import net.quickwrite.fluent4j.impl.ast.entry.FluentBaseElement;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class FluentSelectExpression<B extends ResultBuilder> implements FluentSelect<B>, FluentPlaceable<B> {
    private final FluentSelect.Selectable<B> selectable;
    private final List<FluentSelect.FluentVariant<B>> variantList;
    private final FluentSelect.FluentVariant<B> defaultVariant;

    public FluentSelectExpression(final FluentSelect.Selectable<B> selectable,
                                  final List<FluentSelect.FluentVariant<B>> variantList,
                                  final FluentSelect.FluentVariant<B> defaultVariant
    ) {
        this.selectable = selectable;
        this.variantList = variantList;
        this.defaultVariant = defaultVariant;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        final Function<FluentSelect.FluentVariant<B>, Boolean> selectChecker;
        try {
            selectChecker = selectable.selectChecker(scope);
        } catch (final FluentSelectException ignored) {
            defaultVariant.resolve(scope, builder);
            return;
        }

        for (final FluentSelect.FluentVariant<B> variant : variantList) {
            if (!selectChecker.apply(variant)) {
                continue;
            }

            variant.resolve(scope, builder);
            return;
        }

        defaultVariant.resolve(scope, builder);
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return this;
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return toString();
    }

    public static class FluentVariant<B extends ResultBuilder> extends FluentBaseElement<FluentSelect.FluentVariant.FluentVariantKey<B>, B> implements FluentSelect.FluentVariant<B> {
        public FluentVariant(final FluentSelect.FluentVariant.FluentVariantKey<B> identifier, final List<FluentPattern<B>> content) {
            super(new FluentVariantIdentifier<>(identifier), content);
        }

        @Override
        public FluentIdentifier<FluentSelect.FluentVariant.FluentVariantKey<B>> getIdentifier() {
            return this.identifier;
        }

        private static class FluentVariantIdentifier<B extends ResultBuilder> implements FluentIdentifier<FluentSelect.FluentVariant.FluentVariantKey<B>> {
            private final FluentSelect.FluentVariant.FluentVariantKey<B> identifier;

            public FluentVariantIdentifier(final FluentSelect.FluentVariant.FluentVariantKey<B> identifier) {
                this.identifier = identifier;
            }

            @Override
            public FluentSelect.FluentVariant.FluentVariantKey<B> getSimpleIdentifier() {
                return this.identifier;
            }

            @Override
            public FluentSelect.FluentVariant.FluentVariantKey<B> getFullIdentifier() {
                return this.identifier;
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final FluentVariantIdentifier<?> that = (FluentVariantIdentifier<?>) o;
                return Objects.equals(identifier, that.identifier);
            }

            @Override
            public int hashCode() {
                return Objects.hash(identifier);
            }
        }
    }
}
