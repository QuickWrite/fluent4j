package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

public class FluentTextElement implements FluentPattern, FluentPlaceable, ArgumentList.NamedArgument, FluentSelect.Selectable {
    private final String content;

    public FluentTextElement(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "FluentTextElement{" +
                "content='" + content + '\'' +
                '}';
    }
}
