package net.quickwrite.fluent4j.ast;

public abstract class FluentPlaceable extends FluentElement {

    public static class StringLiteral extends FluentPlaceable {
        private final String content;

        public StringLiteral(String content) {
            this.content = content;
        }
    }

    public static class MessageReference extends FluentPlaceable {
        private final String content;

        public MessageReference(String content) {
            this.content = content;
        }
    }
}
