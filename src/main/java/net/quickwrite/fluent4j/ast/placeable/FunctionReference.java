package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentFunction;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

/**
 * Functions provide additional functionality available to the localizers.
 * They can be either used to format data according to the current language's
 * rules or can provide additional data that the localizer may use (like, the
 * platform, or time of the day) to fine tune the translation.
 */
public class FunctionReference extends FluentFunction implements FluentSelectable {
    private final String functionNameString;

    public FunctionReference(final String functionName, final StringSlice content) {
        super(functionName, content);

        this.functionNameString = functionName;
    }

    public FluentArgument getArgumentResult(final FluentBundle bundle, final FluentArgs arguments) {
        return bundle
                .getFunction(this.functionNameString)
                .getResult(bundle, this.getArguments(bundle, arguments));
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        try {
            return this.getArgumentResult(bundle, arguments).getResult(bundle, arguments);
        } catch (Exception exception) {
            return "{" + functionNameString + "()}";
        }
    }

    @Override
    public String toString() {
        return "FluentFunctionReference: {\n" +
                "\t\t\tfunctionName: \"" + this.functionName + "\"\n" +
                "\t\t\targuments: " + this.arguments + "\n" +
                "\t\t}";
    }
}
