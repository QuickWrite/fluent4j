package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;

public class FluentTextElement implements FluentPattern, FluentPlaceable, ArgumentList.NamedArgument, FluentSelect.Selectable {
    private final String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        appendable.append(content);
    }

    @Override
    public boolean selectCheck(final FluentScope scope, final FluentSelect.FluentVariant variant) {
        return variant.getIdentifier().getSimpleIdentifier().equals(content);
    }
}
