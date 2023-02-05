package net.quickwrite.fluent4j.impl.ast.pattern.container;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentArgumentContainer implements ArgumentList {
    private final Map<String, NamedArgument> named = new HashMap<>();
    private final List<FluentPattern> positional = new ArrayList<>();

    @Override
    public NamedArgument getArgument(final String name) {
        return this.named.get(name);
    }

    public void addArgument(final String name, final NamedArgument argument) {
        this.named.put(name, argument);
    }

    @Override
    public FluentPattern getArgument(final int index) {
        return this.positional.get(index);
    }

    public void addAttribute(final FluentPattern pattern) {
        this.positional.add(pattern);
    }
}
