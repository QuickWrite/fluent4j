package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.parser.StringSlice;

import java.util.List;

public class FluentMessage extends FluentAttribute {
    protected List<FluentAttribute> attributes;

    public FluentMessage(StringSlice identifier, StringSlice content, List<FluentAttribute> attributes) {
        super(identifier, content);

        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "FluentMessage: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tattributes: " + this.attributes + "\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
