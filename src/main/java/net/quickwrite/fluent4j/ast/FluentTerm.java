package net.quickwrite.fluent4j.ast;

public class FluentTerm extends FluentMessage {
    public FluentTerm(String identifier, String content) {
        super(identifier, content);
    }

    @Override
    public String toString() {
        return "FluentTerm: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
