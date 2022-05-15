package net.quickwrite.fluent4j.ast;

import java.util.List;

public abstract class FluentPlaceable extends FluentElement {
    public static class StringLiteral extends FluentPlaceable {
        private final String content;

        public StringLiteral(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "FluentStringLiteral: {\n" +
                    "\t\t\tcontent: " + this.content + "\n" +
                    "\t\t}";
        }
    }

    public static class MessageReference extends FluentPlaceable {
        private final String content;

        public MessageReference(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "FluentMessageReference: {\n" +
                    "\t\t\tcontent: " + this.content + "\n" +
                    "\t\t}";
        }
    }

    public static class SelectExpression extends FluentPlaceable {
        private final List<FluentVariant> variants;
        private final FluentPlaceable identifier;

        public SelectExpression(FluentPlaceable identifier, List<FluentVariant> variants) {
            this.identifier = identifier;
            this.variants = variants;
        }

        @Override
        public String toString() {
            return "FluentSelectExpression: {\n" +
                    "\t\t\tidentifier: " + this.identifier + "\n" +
                    "\t\t\tvariants: " + this.variants + "\n" +
                    "\t\t}";
        }
    }
}
