package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;

public interface FluentPlaceable extends FluentPattern {
    interface CannotPlaceable {
        String getName();
    }
}
