package net.quickwrite.fluent4j.util.bundle;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.exception.FluentParseException;

import java.util.List;

/**
 * A immutable class that is holding the generated AST by the parser
 * for a single file.
 *
 * <p>
 * All of the entries that exist are stored inside of a list.
 */
public class SimpleFluentResource implements FluentResource {
    private final List<FluentElement> elements;
    private final List<FluentParseException> exceptionList;

    /**
     * A SimpleFluentResource is a structure storing parsed localization entries.
     *
     * @param elementList   The list of {@link FluentElement}s.
     * @param exceptionList The list of {@link FluentParseException}s that result from the parsing.
     */
    public SimpleFluentResource(final List<FluentElement> elementList, final List<FluentParseException> exceptionList) {
        this.elements = elementList;
        this.exceptionList = exceptionList;
    }

    public List<FluentElement> getElements() {
        return elements;
    }

    public boolean hasExceptions() {
        return !exceptionList.isEmpty();
    }

    public List<FluentParseException> getExceptions() {
        return exceptionList;
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
