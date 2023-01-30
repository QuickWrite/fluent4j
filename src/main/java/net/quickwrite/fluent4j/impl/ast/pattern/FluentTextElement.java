package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;

public class FluentTextElement implements FluentPattern {
    private final String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
