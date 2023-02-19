package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;

public abstract class FluentEntryBase<B extends ResultBuilder> extends FluentBaseElement<String, B> implements FluentEntry<B> {
    protected final List<FluentEntry.Attribute<B>> attributes;

    public FluentEntryBase(final FluentIdentifier<String> identifier, final List<FluentPattern<B>> patterns, final List<FluentEntry.Attribute<B>> attributes) {
        super(identifier, patterns);

        this.attributes = attributes;
    }

    @Override
    public FluentIdentifier<String> getIdentifier() {
        return this.identifier;
    }

    @Override
    public List<Attribute<B>> getAttributes() {
        return this.attributes;
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return this;
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        final B builder = scope.builder().getSimpleBuilder();

        this.resolve(scope, builder);

        return builder.toString();
    }
}
