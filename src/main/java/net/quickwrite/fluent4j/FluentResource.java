package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.ast.FluentElement;

import java.util.List;

public class FluentResource {
    private final List<FluentElement> body;

    public FluentResource(List<FluentElement> elementList) {
        this.body = elementList;
    }

    @Override
    public String toString() {
        return "FluentResource: {\n" +
                "\tbody: [\n\t\t" +
                    this.body +
                "\n\t]\n" +
                "}";
    }
}
