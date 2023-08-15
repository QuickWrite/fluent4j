package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.nio.CharBuffer;

public record IntermediateTextElement(CharBuffer content, int whitespace, boolean isAfterNL)
        implements FluentPattern, FluentPlaceable, ArgumentList.NamedArgument, FluentSelect.Selectable {

    public CharBuffer slice(final int whitespace) {
        int start = whitespace;
        if (content.length() < whitespace || whitespace == -1) {
            start = 0;
        }

        return content.subSequence(start, content.length());
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        builder.append(slice(whitespace));
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        return slice(whitespace).toString();
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        return this;
    }

    @Override
    public FluentSelect.FluentVariant select(final FluentScope scope,
                                             final FluentSelect.FluentVariant[] variants,
                                             final FluentSelect.FluentVariant defaultVariant
    ) {
        for (final FluentSelect.FluentVariant variant : variants) {
            if (slice(whitespace).toString().equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope))) {
                return variant;
            }
        }

        return defaultVariant;
    }
}
