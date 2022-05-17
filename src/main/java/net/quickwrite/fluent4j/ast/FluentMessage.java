package net.quickwrite.fluent4j.ast;

import java.util.List;

public class FluentMessage extends FluentAttribute {
    protected List<FluentAttribute> attributes;

    public FluentMessage(String identifier, String content, List<FluentAttribute> attributes) {
        super(identifier, content);

        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "FluentMessage: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\tattributes: " + this.attributes + "\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
