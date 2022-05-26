package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.exception.FluentParseException;
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

    public static class VariableReference extends FluentPlaceable {
        protected final StringSlice content;

        public VariableReference(StringSlice content) {
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

    public static class MessageReference extends VariableReference {
        public MessageReference(StringSlice content) {
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
        protected final StringSlice functionName;
        protected final StringSlice content;

        public FunctionReference(StringSlice functionName, StringSlice content) {
            this.functionName = functionName;
            if (!check(functionName)) {
                // TODO: Better Error handling
                throw new FluentParseException("The callee has to be an upper-case identifier or a term");
            }

            this.content = content;
        }

        public StringSlice getContent() {
            return this.content;
        }

        protected boolean check(StringSlice string) {
            while (!string.isBigger()) {
                if(!Character.isUpperCase(string.getChar())) {
                    return false;
                }

                string.increment();
            }

            string.setIndex(0);

            return true;
        }

        @Override
        public String toString() {
            return "FluentFunctionReference: {\n" +
                    "\t\t\tfunctionName: \"" + this.functionName + "\"\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }

    public static class TermReference extends FunctionReference {
        public TermReference(StringSlice functionName, StringSlice content) {
            super(functionName, content);
        }

        @Override
        protected boolean check(StringSlice string) {
            return true;
        }

        @Override
        public String toString() {
            return "FluentTermReference: {\n" +
                    "\t\t\ttermName: \"" + this.functionName + "\"\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }
}
