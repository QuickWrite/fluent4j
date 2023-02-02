package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

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

    @Override
    }
}
