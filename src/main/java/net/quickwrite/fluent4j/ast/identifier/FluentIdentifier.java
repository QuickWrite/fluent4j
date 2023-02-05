package net.quickwrite.fluent4j.ast.identifier;

public interface FluentIdentifier {
    String getSimpleIdentifier();
    String getFullIdentifier();

    int hashCode();

    boolean equals(final Object o);
}
