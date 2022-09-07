package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
        FluentArgs arguments = new FluentArgs();

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

    protected FluentArgs getArguments(FluentBundle bundle, FluentArgs arguments) {
        this.arguments.sanitize(bundle, arguments);
        return this.arguments;
    }

    @Override
    public boolean matches(final FluentBundle bundle, final FluentArgument selector) {
        return this.equals(selector);
    }

    @Override
    public String stringValue() {
        return this.functionName;
    }

    protected boolean check(final String string) {
        for (int i = 0; i < string.length(); i++) {
            final char character = string.charAt(i);

            if (!Character.isUpperCase(character)
                    && !Character.isDigit(character)
                    && character != '-'
                    && character != '_') {
                return false;
            }
        }

        return true;
    }
}
