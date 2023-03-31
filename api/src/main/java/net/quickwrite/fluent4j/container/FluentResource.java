package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;

public interface FluentResource<B extends ResultBuilder> {
    List<FluentEntry<B>> entries();

    FluentEntry<B> entry(final int index);
}
