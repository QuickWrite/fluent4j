package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.entry.FluentBaseElement;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.Objects;

public class FluentSelectExpression implements FluentSelect, FluentPlaceable {
    private final FluentSelect.Selectable selectable;
    private final List<FluentSelect.FluentVariant> variantList;
    private final FluentSelect.FluentVariant defaultVariant;

    public FluentSelectExpression(final FluentSelect.Selectable selectable,
                                  final List<FluentSelect.FluentVariant> variantList,
                                  final FluentSelect.FluentVariant defaultVariant
    ) {
        this.selectable = selectable;
        this.variantList = variantList;
        this.defaultVariant = defaultVariant;
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        final Selectable.SelectChecker selectChecker = selectable.selectChecker(scope);

        if(selectChecker == null) {
            defaultVariant.resolve(scope, builder);
            return;
        }

        for (final FluentSelect.FluentVariant variant : variantList) {
            if (!selectChecker.check(variant)) {
                continue;
            }

            variant.resolve(scope, builder);
            return;
        }

        defaultVariant.resolve(scope, builder);
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        return this;
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        return toString();
    }

    public static class FluentVariant extends FluentBaseElement<FluentSelect.FluentVariant.FluentVariantKey> implements FluentSelect.FluentVariant {
        public FluentVariant(final FluentSelect.FluentVariant.FluentVariantKey identifier, final List<FluentPattern> content) {
            super(new FluentVariantIdentifier(identifier), content);
        }

        @Override
        public FluentIdentifier<FluentSelect.FluentVariant.FluentVariantKey> getIdentifier() {
            return this.identifier;
        }

        private static class FluentVariantIdentifier implements FluentIdentifier<FluentSelect.FluentVariant.FluentVariantKey> {
            private final FluentSelect.FluentVariant.FluentVariantKey identifier;

            public FluentVariantIdentifier(final FluentSelect.FluentVariant.FluentVariantKey identifier) {
                this.identifier = identifier;
            }

            @Override
            public FluentSelect.FluentVariant.FluentVariantKey getSimpleIdentifier() {
                return this.identifier;
            }

            @Override
            public FluentSelect.FluentVariant.FluentVariantKey getFullIdentifier() {
                return this.identifier;
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                final FluentVariantIdentifier that = (FluentVariantIdentifier) o;
                return Objects.equals(identifier, that.identifier);
            }

            @Override
            public int hashCode() {
                return Objects.hash(identifier);
            }
        }
    }
}
