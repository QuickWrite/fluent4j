package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.exception.FluentParseException;

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
    private final List<FluentElement> elements;
    private final List<FluentParseException> exceptionList;

    public FluentResource(List<FluentElement> elementList, List<FluentParseException> exceptionList) {
        this.elements = elementList;
        this.exceptionList = exceptionList;
    }

    public List<FluentElement> getElements() {
        return elements;
    }

    public boolean hasExceptions() {
        return !exceptionList.isEmpty();
    }

    @Override
    public String toString() {
        return "FluentResource: {\n" +
                "\telements: [\n\t\t" +
                    this.elements +
                "\n\t]\n" +
                "\texceptions: [\n\t\t" +
                this.exceptionList +
                "\n\t]\n" +
                "}";
    }
}
