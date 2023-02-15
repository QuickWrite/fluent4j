package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentMessage;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

import java.util.List;
import java.util.Objects;

public final class FluentMessageElement extends FluentEntryBase implements FluentMessage {
    public FluentMessageElement(final String identifier, final List<FluentPattern> patterns, final List<FluentEntry.Attribute> attributes) {
        super(new FluentMessageIdentifier(identifier), patterns, attributes);
    }

    private static class FluentMessageIdentifier implements FluentIdentifier<String> {
        private final String identifier;

        public FluentMessageIdentifier(final String identifier) {
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
            final FluentMessageIdentifier that = (FluentMessageIdentifier) o;
            return Objects.equals(identifier, that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifier);
        }
    }
}
