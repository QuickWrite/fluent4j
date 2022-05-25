package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.parser.StringSlice;

import java.util.List;

public abstract class FluentPlaceable extends FluentElement {
    public abstract StringSlice getContent();

    public static class StringLiteral extends FluentPlaceable {
        private final StringSlice content;

        public StringLiteral(StringSlice content) {
            this.content = content;
        }

        public StringSlice getContent() {
            return this.content;
        }

        @Override
        public String toString() {
            return "FluentStringLiteral: {\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }

    public static class MessageReference extends FluentPlaceable {
        protected final StringSlice content;

        public MessageReference(StringSlice content) {
            this.content = content;
        }

        public StringSlice getContent() {
            return this.content;
        }

        @Override
        public String toString() {
            return "FluentMessageReference: {\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }

    public static class VariableReference extends MessageReference {
        public VariableReference(StringSlice content) {
            super(content);
        }

        @Override
        public String toString() {
            return "FluentVariableReference: {\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
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

        public StringSlice getContent() {
            return this.identifier.getContent();
        }

        @Override
        public String toString() {
            return "FluentSelectExpression: {\n" +
                    "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                    "\t\t\tvariants: " + this.variants + "\n" +
                    "\t\t}";
        }
    }

    public static class FunctionReference extends FluentPlaceable {
        private final StringSlice functionName;
        private final StringSlice content;

        public FunctionReference(StringSlice functionName, StringSlice content) {
            this.functionName = functionName;
            this.content = content;
        }

        public StringSlice getContent() {
            return this.content;
        }

        @Override
        public String toString() {
            return "FluentFunctionReference: {\n" +
                    "\t\t\tfunctionName: \"" + this.functionName + "\"\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }
}
