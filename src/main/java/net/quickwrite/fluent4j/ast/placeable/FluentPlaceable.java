package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.result.ResultBuilder;

public interface FluentPlaceable<B extends ResultBuilder> extends FluentPattern<B> {
    interface CannotPlaceable {
        String getName();
    }
}
