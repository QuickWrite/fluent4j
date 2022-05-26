package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.ast.FluentElement;

import java.util.List;

/**
 * A immutable class that is holding the generated AST by the parser
 * for a single file.
 *
 * <p>
 *     All of the entries that exist are stored inside of a list.
 * </p>
 *
 */
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
