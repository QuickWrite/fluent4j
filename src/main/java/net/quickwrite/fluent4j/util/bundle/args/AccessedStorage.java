package net.quickwrite.fluent4j.util.bundle.args;

import net.quickwrite.fluent4j.ast.FluentBase;

public interface AccessedStorage {
    boolean alreadyAccessed(final FluentBase element);

    void addElement(final FluentBase element);
}
