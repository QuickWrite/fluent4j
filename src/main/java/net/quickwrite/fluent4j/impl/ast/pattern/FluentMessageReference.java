package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;

public class FluentMessageReference implements FluentPlaceable {
    protected final String identifier;

    public FluentMessageReference(final String identifier) {
        this.identifier = identifier;
    }

    public static class AttributeReference extends FluentMessageReference {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier) {
            super(identifier);

            this.attributeIdentifier = attributeIdentifier;
        }
    }
}
