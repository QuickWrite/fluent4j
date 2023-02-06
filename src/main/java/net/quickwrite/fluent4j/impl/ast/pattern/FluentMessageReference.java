package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;

public class FluentMessageReference implements FluentPlaceable {
    protected final String identifier;

    public FluentMessageReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        // TODO: Don't just throw
        final FluentEntry message = scope.getBundle().getMessage(identifier).orElseThrow();

        message.resolve(scope, appendable);
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        return this;
    }

    public static class AttributeReference extends FluentMessageReference {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier) {
            super(identifier);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
            // TODO: Don't just throw
            final FluentEntry message = scope.getBundle().getMessage(identifier).orElseThrow();

            final FluentEntry.Attribute attribute = message.getAttribute(attributeIdentifier).orElseThrow();

            attribute.resolve(scope, appendable);
        }
    }
}
