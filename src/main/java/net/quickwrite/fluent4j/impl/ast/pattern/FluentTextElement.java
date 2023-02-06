package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;
import java.util.function.Function;

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
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        appendable.append(content);
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
    public Function<FluentSelect.FluentVariant, Boolean> selectChecker(final FluentScope scope) {
        return variant -> {
            try {
                return content.equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope));
            } catch (final IOException exception) {
                throw new RuntimeException(exception);
            }
        };
    }
}
