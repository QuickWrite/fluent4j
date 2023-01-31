package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;

public class FluentFunction extends ParameterizedLiteral<String> {

    public FluentFunction(final String identifier, final AttributeList attributes) {
        super(identifier, attributes);
    }

    @Override
    public boolean canSelect() {
        return true;
    }
}
