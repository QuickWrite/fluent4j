package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

import java.util.List;
import java.util.Objects;

public class FluentAttribute extends FluentBaseElement<String> implements FluentEntry.Attribute {
    public FluentAttribute(final String identifier, final List<FluentPattern> patterns) {
        super(new FluentAttributeIdentifier(identifier), patterns);
    }

    @Override
    public FluentIdentifier<String> getIdentifier() {
        return this.identifier;
    }

    @Override
    public List<FluentPattern> getPatterns() {
        return this.patterns;
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
