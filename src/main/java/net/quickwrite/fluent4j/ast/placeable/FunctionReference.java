package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentFunction;
import net.quickwrite.fluent4j.util.StringSlice;

/**
 * Functions provide additional functionality available to the localizers.
 * They can be either used to format data according to the current language's
 * rules or can provide additional data that the localizer may use (like, the
 * platform, or time of the day) to fine tune the translation.
 */
public class FunctionReference extends FluentFunction implements FluentSelectable {
    public FunctionReference(StringSlice functionName, StringSlice content) {
        super(functionName, content);
    }

    @Override
    public String toString() {
        return "FluentFunctionReference: {\n" +
                "\t\t\tfunctionName: \"" + this.functionName + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\tpositionalArguments: \"" + this.positionalArgumentList + "\"\n" +
                "\t\t\tnamedArguments: \"" + this.namedArgumentList + "\"\n" +
                "\t\t}";
    }
}
