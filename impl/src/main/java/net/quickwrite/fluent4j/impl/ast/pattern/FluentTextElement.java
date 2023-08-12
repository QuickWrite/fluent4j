package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

public class FluentTextElement implements
        FluentPattern,
        FluentPlaceable,
        ArgumentList.NamedArgument,
        FluentSelect.Selectable,
        FluentSelect.FluentVariant.FluentVariantKey
{
    private final String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        builder.append(content);
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        return this.content;
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        return this;
    }

    @Override
    public SelectChecker selectChecker(final FluentScope scope) {
        return variant -> content.equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope));
    }
}
