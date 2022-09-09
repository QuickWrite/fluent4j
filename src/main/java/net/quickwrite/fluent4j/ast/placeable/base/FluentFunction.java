package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.ResourceFluentBundle;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import net.quickwrite.fluent4j.util.args.ResourceFluentArguments;
import net.quickwrite.fluent4j.util.args.FluentArgument;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Implements the basis for a value that gets
 * parameters and calls another element of data so
 * that it can operate.
 */
public abstract class FluentFunction implements FluentPlaceable, FluentArgumentResult {
    protected final String functionName;
    protected final FluentArgs arguments;

    public FluentFunction(final StringSlice functionName, final StringSlice content) {
        this(functionName.toString(), content);
    }

    public FluentFunction(final String functionName, final StringSlice content) {
        this.functionName = functionName;
        if (!check(functionName)) {
            // TODO: Better Error handling
            throw new FluentParseException("The callee has to be an upper-case identifier or a term");
        }

        this.arguments = (content == null) ? FluentArgs.EMPTY_ARGS : this.getArguments(content);
    }

    private FluentArgs getArguments(final StringSlice content) {
        FluentArgs arguments = new ResourceFluentArguments();

        while (!content.isBigger()) {
            StringSliceUtil.skipWhitespaceAndNL(content);

            Pair<String, FluentArgument> argument = getArgument(content);
            if (argument.getLeft() != null) {
                arguments.setNamed(argument.getLeft(), argument.getRight());
            } else {
                arguments.addPositional(argument.getRight());
            }

            StringSliceUtil.skipWhitespaceAndNL(content);

            if (content.getChar() != ',') {
                if (!content.isBigger()) {
                    throw new FluentParseException("','", content.getCharUTF16(), content.getAbsolutePosition());
                }
                break;
            }
            content.increment();
        }

        return arguments;
    }

    private Pair<String, FluentArgument> getArgument(final StringSlice content) {
        FluentPlaceable placeable = StringSliceUtil.getExpression(content);
        String identifier = null;

        StringSliceUtil.skipWhitespace(content);

        if (content.getChar() == ':') {
            content.increment();
            StringSliceUtil.skipWhitespace(content);

            identifier = placeable.stringValue();

            placeable = StringSliceUtil.getExpression(content);
        }

        return new ImmutablePair<>(identifier, placeable);
    }

    /**
     * Returns the arguments that the function
     * has itself in a sanitized form.
     *
     * @param bundle The bundle that this is being called from
     * @param arguments The arguments in this scope
     * @return The sanitized arguments of the function
     */
    protected FluentArgs getArguments(final ResourceFluentBundle bundle, final FluentArgs arguments) {
        this.arguments.sanitize(bundle, arguments);
        return this.arguments;
    }

    /**
     * Returns the {@link FluentArgument} that the function is returning.
     *
     * @param bundle The bundle that this is being called from
     * @param arguments The arguments that are passed into this function
     * @return The resulting {@link FluentArgument} that has been created
     */
    public abstract FluentArgument getArgumentResult(final ResourceFluentBundle bundle, final FluentArgs arguments);

    /**
     * Checks if this FluentFunction and the selector are the same.
     *
     * @param bundle The base bundle
     * @param selector The other element
     * @return If they are the same object
     */
    @Override
    public boolean matches(final ResourceFluentBundle bundle, final FluentArgument selector) {
        return this.equals(selector);
    }

    /**
     * Returns the function name that is being
     * used.
     *
     * @return The {@code functionName}
     */
    @Override
    public String stringValue() {
        return this.functionName;
    }

    /**
     * Checks if the name is a correct function name.
     *
     * @param string The function name
     * @return If it is a correct function name
     */
    protected abstract boolean check(final String string);
}
