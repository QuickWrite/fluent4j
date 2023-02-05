package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTerm;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class FluentTermReference extends ParameterizedLiteral<String> {
    public FluentTermReference(final String identifier, final ArgumentList argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        // TODO: Don't just throw
        final FluentTerm term = scope.getBundle().getEntry(identifier, FluentTerm.class).orElseThrow();

        term.resolve(scope, appendable);
    }

    public static class AttributeReference extends FluentTermReference implements FluentPlaceable.CannotPlaceable, FluentSelect.Selectable {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier, final ArgumentList argumentList) {
            super(identifier, argumentList);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
            final FluentEntry.Attribute attribute = getAttribute(scope);

            attribute.resolve(scope, appendable);
        }

        @Override
        public String getName() {
            return "Term Attribute";
        }

        private Map.Entry<FluentScope, String> resolveCache = null;

        private FluentEntry.Attribute getAttribute(final FluentScope scope) {
            // TODO: Don't just throw
            final FluentTerm term = scope.getBundle().getEntry(identifier, FluentTerm.class).orElseThrow();

            return term.getAttribute(attributeIdentifier).orElseThrow();
        }

        @Override
        public synchronized boolean selectCheck(final FluentScope scope, final FluentSelect.FluentVariant variant) {
            final FluentEntry.Attribute attribute = getAttribute(scope);
            if (attribute.getPatterns().size() != 1) {
                return false;
            }

            if (resolveCache != null && resolveCache.getKey() == scope) {
                return resolveCache.getValue().equals(variant.getIdentifier().getSimpleIdentifier());
            }

            final StringBuilder builder = new StringBuilder();
            try {
                attribute.resolve(scope, builder);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }

            this.resolveCache = new AbstractMap.SimpleImmutableEntry<>(scope, builder.toString());

            return this.resolveCache.getValue().equals(variant.getIdentifier().getSimpleIdentifier());
        }

        @Override
        public void endSelect() {
            resolveCache = null;
        }
    }
}
