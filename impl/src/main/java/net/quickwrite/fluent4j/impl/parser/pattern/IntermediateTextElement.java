package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.nio.CharBuffer;

public class IntermediateTextElement<B extends ResultBuilder> implements FluentPattern<B>, FluentPlaceable<B>, ArgumentList.NamedArgument<B>, FluentSelect.Selectable<B> {
    private final CharBuffer content;
    private final boolean isAfterNL;

    private final int whitespace;

    public IntermediateTextElement(final CharBuffer content, final int whitespace, final boolean isAfterNL) {
        this.content = content;
        this.whitespace = whitespace;
        this.isAfterNL = isAfterNL;
    }

    public CharBuffer getContent() {
        return this.content;
    }

    public int getWhitespace() {
        return this.whitespace;
    }

    public boolean isAfterNL() {
        return this.isAfterNL;
    }

    public CharBuffer slice(final int whitespace) {
        int start = whitespace;
        if (content.length() < whitespace || whitespace == -1) {
            start = 0;
        }

        return content.subSequence(start, content.length());
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        builder.append(slice(whitespace));
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return slice(whitespace).toString();
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return this;
    }

    @Override
    public SelectChecker<B> selectChecker(final FluentScope<B> scope) {
        return (variant) -> slice(whitespace).toString().equals(variant.getIdentifier().getSimpleIdentifier().toSimpleString(scope));
    }
}
