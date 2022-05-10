package net.quickwrite.fluent4j.ast;

import java.util.ArrayList;
import java.util.List;

public class FluentAttribute extends FluentElement {
    protected final String identifier;

    protected final List<FluentElement> fluentElements;
    protected final String content;

    protected int index = 0;

    public FluentAttribute(String identifier, String content) {
        this.identifier = identifier;

        this.content = content;

        this.fluentElements = parse();
    }

    private List<FluentElement> parse() {
        List<FluentElement> elements = new ArrayList<>();

        return elements;
    }

    @Override
    public String toString() {
        return "FluentAttribute: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: " + this.content + "\n" +
                "\t\t}";
    }
}
