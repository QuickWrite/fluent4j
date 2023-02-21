package net.quickwrite.fluent4j.impl.ast.pattern.container;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentArgumentContainer<B extends ResultBuilder> implements ArgumentList<B> {
    private final Map<String, NamedArgument<B>> named = new HashMap<>();
    private final List<FluentPattern<B>> positional = new ArrayList<>();

    @Override
    public NamedArgument<B> getArgument(final String name) {
        return this.named.get(name);
    }

    public void addArgument(final String name, final NamedArgument<B> argument) {
        this.named.put(name, argument);
    }

    @Override
    public FluentPattern<B> getArgument(final int index) {
        return this.positional.get(index);
    }

    public void addAttribute(final FluentPattern<B> pattern) {
        this.positional.add(pattern);
    }
}
