package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;

public class IntermediateTextElement implements FluentPattern {
    private final CharSequence content;
    private final boolean isAfterNL;

    public IntermediateTextElement(final CharSequence content, final boolean isAfterNL) {
        this.content = content;
        this.isAfterNL = isAfterNL;
    }
}
