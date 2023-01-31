package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;

public class FluentNumberLiteral implements FluentPattern {
    // TODO: Better number storing, formatting etc.
    private final String number;

    public FluentNumberLiteral(final String number) {
        this.number = number;
    }
}
