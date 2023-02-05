package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

import java.util.List;
import java.util.Objects;

public final class FluentTerm extends FluentEntryBase implements FluentEntry {
    public FluentTerm(final String identifier, final List<FluentPattern> patterns, final List<FluentEntry.Attribute> attributes) {
        super(new FluentTermIdentifier(identifier), patterns, attributes);
    }

    private static class FluentTermIdentifier implements FluentIdentifier {
        private final String identifier;

        public FluentTermIdentifier(final String identifier) {
            this.identifier = identifier;
        }

        @Override
        public String getSimpleIdentifier() {
            return this.identifier;
        }

        @Override
        public String getFullIdentifier() {
            return "-" + this.identifier;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final FluentTerm.FluentTermIdentifier that = (FluentTerm.FluentTermIdentifier) o;
            return Objects.equals(identifier, that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash("-") + Objects.hash(identifier);
        }
    }
}
