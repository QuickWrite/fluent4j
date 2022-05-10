package net.quickwrite.fluent4j.ast;

import java.util.List;

public class FluentTerm extends FluentMessage {
    public FluentTerm(String identifier, String content, List<FluentAttribute> attributes) {
        super(identifier, content, attributes);
    }

    @Override
    public String toString() {
        return "FluentTerm: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: " + this.content + "\n" +
                "\t\t\tattributes: " + this.attributes + "\n" +
                "\t\t}";
    }
}
