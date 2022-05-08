package net.quickwrite.fluent4j.ast;

public class FluentMessage extends FluentElement {
    protected final String identifier;
    protected final String content;

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
