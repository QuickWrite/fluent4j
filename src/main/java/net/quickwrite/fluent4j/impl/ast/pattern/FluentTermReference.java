package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentTermReference extends ParameterizedLiteral<String> {
    public FluentTermReference(final String identifier, final AttributeList attributeList) {
        super(identifier, attributeList);
    }

    public static class AttributeReference extends FluentTermReference implements FluentPlaceable.CannotPlaceable, FluentSelect.Selectable {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier, final AttributeList attributeList) {
            super(identifier, attributeList);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public String getName() {
            return "Term Attribute";
        }
    }
}
