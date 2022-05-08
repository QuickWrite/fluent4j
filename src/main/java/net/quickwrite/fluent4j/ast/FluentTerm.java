package net.quickwrite.fluent4j.ast;

public class FluentTerm extends FluentElement {
    private final String identifier;
    private final String content;

    public FluentTerm(String identifier, String content) {
        this.identifier = identifier;
        this.content = content;
    }

    @Override
    public String toString() {
        return "FluentTerm: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
