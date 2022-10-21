package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.placeable.base.FluentFunction;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FunctionFluentArgs;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

/**
 * Functions provide additional functionality available to the localizers.
 * They can be either used to format data according to the current language's
 * rules or can provide additional data that the localizer may use (like, the
 * platform, or time of the day) to fine tune the translation.
 */
public class FunctionReference extends FluentFunction implements FluentSelectable {

    public FunctionReference(final String functionName, final FluentArgs arguments) {
        super(functionName, arguments);
    }

    @Override
    public FluentElement getArgumentResult(final AccessorBundle bundle, int recursionDepth) {
        try {
            return bundle
                    .getBundle()
                    .getFunction(this.functionName)
                    .orElseThrow()
                    .getFunctionResult(
                            bundle,
                            (FunctionFluentArgs) this.getArguments(bundle, recursionDepth),
                            recursionDepth - 1
                    );
        } catch (final Exception exception) {
            return new StringLiteral("{" + functionName + "()}");
        }
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
     *
     * @param bundle @return The result of the function with the specific parameters
     * @param recursionDepth The amount of recursive calls that can be made
     */
    @Override
    public CharSequence getResult(AccessorBundle bundle, final int recursionDepth) {
            return this.getArgumentResult(bundle, recursionDepth - 1).getResult(bundle, recursionDepth - 1);
    }

    @Override
    public String toString() {
        return "FluentFunctionReference: {\n" +
                "\t\t\tfunctionName: \"" + this.functionName + "\"\n" +
                "\t\t\targuments: " + this.arguments + "\n" +
                "\t\t}";
    }
}
