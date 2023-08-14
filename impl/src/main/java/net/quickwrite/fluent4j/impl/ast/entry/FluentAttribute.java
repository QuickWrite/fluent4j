package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

import java.util.List;
import java.util.Objects;

public class FluentAttribute extends FluentBaseElement<String> implements FluentAttributeEntry.Attribute {
    public FluentAttribute(final String identifier, final FluentPattern[] patterns) {
        super(new FluentAttributeIdentifier(identifier), patterns);
    }

    @Override
    public FluentIdentifier<String> getIdentifier() {
        return this.identifier;
    }

    @Override
    public boolean isSelectable() {
        return this.patterns.length == 1;
    }

    private static class FluentAttributeIdentifier implements FluentIdentifier<String> {
        private final String identifier;

        public FluentAttributeIdentifier(final String identifier) {
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
            final FluentAttribute.FluentAttributeIdentifier that = (FluentAttribute.FluentAttributeIdentifier) o;
            return Objects.equals(identifier, that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifier);
        }
    }
}
