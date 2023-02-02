package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentTermReference extends ParameterizedLiteral<String> {
    public FluentTermReference(final String identifier, final ArgumentList argumentList) {
        super(identifier, argumentList);
    }

    public static class AttributeReference extends FluentTermReference implements FluentPlaceable.CannotPlaceable, FluentSelect.Selectable {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier, final ArgumentList argumentList) {
            super(identifier, argumentList);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public String getName() {
            return "Term Attribute";
        }
    }
}
