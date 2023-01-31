package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;

public class FluentTextElement implements FluentPattern, FluentPlaceable, AttributeList.NamedAttribute {
    private final String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean canSelect() {
        return true;
    }
}
