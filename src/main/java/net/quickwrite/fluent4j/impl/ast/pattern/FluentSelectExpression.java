package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;

import java.util.List;

public class FluentSelectExpression implements FluentSelect, FluentPlaceable {
    private final FluentPlaceable placeable;
    private final List<FluentSelect.FluentVariant> variantList;

    public FluentSelectExpression(final FluentPlaceable placeable, final List<FluentSelect.FluentVariant> variantList) {
        this.placeable = placeable;
        this.variantList = variantList;
    }

    public static class FluentVariant implements FluentSelect.FluentVariant {
        private final String key;
        private final List<FluentPattern> content;

        public FluentVariant(final String key, final List<FluentPattern> content) {
            this.key = key;
            this.content = content;
        }
    }
}
