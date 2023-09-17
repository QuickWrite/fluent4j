package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

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

    private record FluentAttributeIdentifier(String identifier) implements FluentIdentifier<String> {

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
