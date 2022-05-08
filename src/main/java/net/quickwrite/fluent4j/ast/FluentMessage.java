package net.quickwrite.fluent4j.ast;

public class FluentMessage extends FluentElement {
    private final String identifier;
    private final String content;

    public FluentMessage(String identifier, String content) {
        this.identifier = identifier;
        this.content = content;
    }

    @Override
    public String toString() {
        return "FluentMessage: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
