package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentAttributeContainer;

public interface AttributeList {
    AttributeList EMPTY = new FluentAttributeContainer();

    NamedAttribute getAttribute(final String name);
    FluentPattern getAttribute(final int index);

    interface NamedAttribute extends FluentPattern {

    }
}
