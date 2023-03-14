package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.Objects;

public final class FluentTermElement<B extends ResultBuilder> extends FluentEntryBase<B> implements FluentEntry<B> {
    public FluentTermElement(final String identifier, final List<FluentPattern<B>> patterns, final List<FluentEntry.Attribute<B>> attributes) {
        super(new FluentTermIdentifier(identifier), patterns, attributes);
    }

    private static class FluentTermIdentifier implements FluentIdentifier<String> {
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
            final FluentTermElement.FluentTermIdentifier that = (FluentTermElement.FluentTermIdentifier) o;
            return Objects.equals(identifier, that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash("-") + Objects.hash(identifier);
        }
    }
}
