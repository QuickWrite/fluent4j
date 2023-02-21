package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.io.IOException;
import java.util.function.Function;

public class FluentTextElement<B extends ResultBuilder> implements
        FluentPattern<B>,
        FluentPlaceable<B>,
        ArgumentList.NamedArgument<B>,
        FluentSelect.Selectable<B>,
        FluentSelect.FluentVariant.FluentVariantKey<B>
{
    private final String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        builder.append(content);
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return this.content;
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return this;
    }

    @Override
    public Function<FluentSelect.FluentVariant<B>, Boolean> selectChecker(final FluentScope<B> scope) {
        return variant -> content.equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope));
    }
}
