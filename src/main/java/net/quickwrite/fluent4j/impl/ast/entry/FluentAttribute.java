package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;

import java.util.List;

public class FluentAttribute extends FluentBaseElement implements FluentEntry.Attribute {
    public FluentAttribute(final String identifier, final List<FluentPattern> patterns) {
        super(identifier, patterns);
    }
}
