package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentTextElement implements FluentPattern, FluentPlaceable, AttributeList.NamedAttribute, FluentSelect.Selectable {
    private final String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
