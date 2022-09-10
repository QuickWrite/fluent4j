package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.placeable.base.FluentFunction;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

/**
 * Functions provide additional functionality available to the localizers.
 * They can be either used to format data according to the current language's
 * rules or can provide additional data that the localizer may use (like, the
 * platform, or time of the day) to fine tune the translation.
 */
public class FunctionReference extends FluentFunction implements FluentSelectable {

    public FunctionReference(final String functionName, final StringSlice content) {
        super(functionName, content);
    }

    @Override
    public FluentElement getArgumentResult(final DirectFluentBundle bundle, final FluentArgs arguments) {
        return bundle
                .getFunction(this.functionName)
                .getResult(bundle, this.getArguments(bundle, arguments));
    }

    /**
     * Checks if the function name is correct.
     *
     * <p>
     * The function name <strong>needs</strong> to be in the format of
     * {@code [A-Z0-9-_]+}.
     *
     * @param string The function name
     * @return If the function name is correct
     */
    @Override
    protected boolean check(String string) {
        for (int i = 0; i < string.length(); i++) {
            final char character = string.charAt(i);

            if (!(Character.isUpperCase(character)
                    || Character.isDigit(character)
                    || character == '-'
                    || character == '_')) {
                return false;
            }
        }

        return true;
    }

    /**
     * Executes the function that this object references and returns
     * it as a {@link CharSequence}.
     *
     * <p>
     * If the function throws an exception it returns a String with
     * the format: <code>{ + functionName + ()}</code>.
     * <br>
     * This means that the {@code NUMBER}-function gets no arguments
     * this function will return <code>{NUMBER()}</code>.
     *
     * @param bundle    The base bundle
     * @param arguments The arguments that are being passed on the scope
     * @return The result of the function with the specific parameters
     */
    @Override
    public CharSequence getResult(final DirectFluentBundle bundle, final FluentArgs arguments) {
        try {
            return this.getArgumentResult(bundle, arguments).getResult(bundle, arguments);
        } catch (Exception exception) {
            return "{" + functionName + "()}";
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
