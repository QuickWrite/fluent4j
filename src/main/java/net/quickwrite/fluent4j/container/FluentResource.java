package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.FluentEntry;

import java.util.List;

public interface FluentResource {
    List<FluentEntry> entries();

    FluentEntry entry(final int index);
}
