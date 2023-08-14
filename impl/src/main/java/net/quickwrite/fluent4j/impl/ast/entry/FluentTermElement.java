package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

import java.util.Objects;

public final class FluentTermElement extends FluentAttributeEntryBase implements FluentEntry {
    public FluentTermElement(final String identifier, final FluentPattern[] patterns, final FluentAttributeEntry.Attribute[] attributes) {
        super(new FluentTermIdentifier(identifier), patterns, attributes);
    }

    private record FluentTermIdentifier(String identifier) implements FluentIdentifier<String> {
        @Override
        public String getSimpleIdentifier() {
            return this.identifier;
        }

        @Override
        public String getFullIdentifier() {
                return "-" + this.identifier;
            }

        @Override
        public int hashCode() {
                return Objects.hash("-", identifier);
            }
        }
}
