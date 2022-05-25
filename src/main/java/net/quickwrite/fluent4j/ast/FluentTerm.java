package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.parser.StringSlice;

import java.util.List;

public class FluentTerm extends FluentMessage {
    public FluentTerm(StringSlice identifier, StringSlice content, List<FluentAttribute> attributes) {
        super(identifier, content, attributes);
    }

    @Override
    public String toString() {
        return "FluentTerm: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tattributes: " + this.attributes + "\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
