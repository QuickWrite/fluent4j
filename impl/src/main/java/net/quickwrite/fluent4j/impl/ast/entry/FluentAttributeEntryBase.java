package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

public abstract class FluentAttributeEntryBase extends FluentBaseElement<String> implements FluentAttributeEntry {
    protected final FluentAttributeEntry.Attribute[] attributes;

    protected FluentAttributeEntryBase(
            final FluentIdentifier<String> identifier,
            final FluentPattern[] patterns,
            final FluentAttributeEntry.Attribute[] attributes
    ) {
        super(identifier, patterns);

        this.attributes = attributes;
    }

    @Override
    public FluentIdentifier<String> getIdentifier() {
        return this.identifier;
    }

    @Override
    public FluentAttributeEntry.Attribute[] getAttributes() {
        return this.attributes;
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        return this;
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        final ResultBuilder builder = scope.builder().getSimpleBuilder();

        this.resolve(scope, builder);

        return builder.toString();
    }
}
