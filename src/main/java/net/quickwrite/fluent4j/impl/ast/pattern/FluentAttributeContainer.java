package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.AttributeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentAttributeContainer implements AttributeList {
    private final Map<String, NamedAttribute> named = new HashMap<>();
    private final List<FluentPattern> positional = new ArrayList<>();

    @Override
    public NamedAttribute getAttribute(final String name) {
        return this.named.get(name);
    }

    public void addAttribute(final String name, final NamedAttribute attribute) {
        this.named.put(name, attribute);
    }

    @Override
    public FluentPattern getAttribute(final int index) {
        return this.positional.get(index);
    }

    public void addAttribute(final FluentPattern pattern) {
        this.positional.add(pattern);
    }
}
