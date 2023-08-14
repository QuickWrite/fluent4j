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
    private final FluentSelect.FluentVariant[] variants;
    private final FluentSelect.FluentVariant defaultVariant;

    public FluentSelectExpression(final FluentSelect.Selectable selectable,
                                  final FluentSelect.FluentVariant[] variants,
                                  final FluentSelect.FluentVariant defaultVariant
    ) {
        this.selectable = selectable;
        this.variants = variants;
        this.defaultVariant = defaultVariant;
    }

    public FluentSelectExpression(final FluentSelect.Selectable selectable,
                                  final List<FluentSelect.FluentVariant> variantList,
                                  final FluentSelect.FluentVariant defaultVariant
    ) {
        this(selectable, variantList.toArray(new FluentSelect.FluentVariant[0]), defaultVariant);
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        selectable.select(scope, variants, defaultVariant).resolve(scope, builder);
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
        public FluentVariant(final FluentSelect.FluentVariant.FluentVariantKey identifier, final FluentPattern[] content) {
            super(new FluentVariantIdentifier(identifier), content);
        }

        @Override
        public FluentIdentifier<FluentSelect.FluentVariant.FluentVariantKey> getIdentifier() {
            return this.identifier;
        }

        private record FluentVariantIdentifier(FluentVariantKey identifier) implements FluentIdentifier<FluentVariantKey> {
            @Override
            public FluentVariantKey getSimpleIdentifier() {
                return this.identifier;
            }

            @Override
            public FluentVariantKey getFullIdentifier() {
                return this.identifier;
            }
        }
    }
}
