package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.entry.FluentBaseElement;

import java.io.IOException;
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
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        for (final FluentSelect.FluentVariant variant : variantList) {
            if (!selectable.selectCheck(scope, variant)) {
                continue;
            }

            variant.resolve(scope, appendable);
            return;
        }

        defaultVariant.resolve(scope, appendable);
    }

    public static class FluentVariant extends FluentBaseElement implements FluentSelect.FluentVariant {
        public FluentVariant(final String identifier, final List<FluentPattern> content) {
            super(new FluentVariantIdentifier(identifier), content);
        }

        @Override
        public FluentIdentifier getIdentifier() {
            return this.identifier;
        }

        private static class FluentVariantIdentifier implements FluentIdentifier {
            private final String identifier;

            public FluentVariantIdentifier(final String identifier) {
                this.identifier = identifier;
            }

            @Override
            public String getSimpleIdentifier() {
                return this.identifier;
            }

            @Override
            public String getFullIdentifier() {
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
