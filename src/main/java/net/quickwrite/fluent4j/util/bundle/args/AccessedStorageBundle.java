package net.quickwrite.fluent4j.util.bundle.args;

import net.quickwrite.fluent4j.ast.FluentBase;

import java.util.HashSet;
import java.util.Set;

public class AccessedStorageBundle implements AccessedStorage {
    private final Set<FluentBase> elementSet;

    public AccessedStorageBundle() {
        this.elementSet = new HashSet<>();
    }

    @Override
    public boolean alreadyAccessed(final FluentBase element) {
        return this.elementSet.contains(element);
    }

    @Override
    public void addElement(final FluentBase element) {
        this.elementSet.add(element);
    }
}
