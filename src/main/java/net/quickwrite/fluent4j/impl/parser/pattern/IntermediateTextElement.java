package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;
import java.nio.CharBuffer;

public class IntermediateTextElement implements FluentPattern, FluentPlaceable, FluentSelect.Selectable {
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

    public CharBuffer slice() {
        int start = this.whitespace;
        if (content.length() < this.whitespace || this.whitespace == -1) {
            start = 0;
        }

        return content.subSequence(start, content.length());
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        appendable.append(slice());
    }

    @Override
    public boolean selectCheck(final FluentScope scope, final FluentSelect.FluentVariant variant) {
        return slice().toString().equals(variant.getIdentifier().getSimpleIdentifier());
    }
}
