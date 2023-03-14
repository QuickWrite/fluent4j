package net.quickwrite.fluent4j.ast.identifier;

public interface FluentIdentifier<I> {
    I getSimpleIdentifier();
    I getFullIdentifier();

    int hashCode();

    boolean equals(final Object o);
}
