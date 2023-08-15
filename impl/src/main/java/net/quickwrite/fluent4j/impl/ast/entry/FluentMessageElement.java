package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.entry.FluentMessage;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

public final class FluentMessageElement extends FluentAttributeEntryBase implements FluentMessage {
    public FluentMessageElement(final String identifier, final FluentPattern[] patterns, final FluentAttributeEntry.Attribute[] attributes) {
        super(new FluentMessageIdentifier(identifier), patterns, attributes);
    }

    private record FluentMessageIdentifier(String identifier) implements FluentIdentifier<String> {
        @Override
        public String getSimpleIdentifier() {
            return this.identifier;
        }

        @Override
        public String getFullIdentifier() {
            return this.identifier;
        }
    }
}
