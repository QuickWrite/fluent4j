package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public abstract class FluentFunction implements FluentPlaceable<FluentArgument<?>>, FluentArgumentResult {
    protected final StringSlice functionName;
    protected final StringSlice content;
    protected FluentArgs arguments;

    public FluentFunction(StringSlice functionName, StringSlice content) {
        this.functionName = functionName;
        if (!check(functionName)) {
            // TODO: Better Error handling
            throw new FluentParseException("The callee has to be an upper-case identifier or a term");
        }

        this.content = content;

        this.arguments = new FluentArgs();

        if (content != null) {
            getArguments();
        }
    }

    private void getArguments() {
        while (!content.isBigger()) {
            StringSliceUtil.skipWhitespaceAndNL(content);

            Pair<StringSlice, FluentArgument<?>> argument = getArgument();
            if (argument.getLeft() != null) {
                this.arguments.setNamed(argument.getLeft().toString(), argument.getRight());
            } else {
                this.arguments.addPositional(argument.getRight());
            }

            StringSliceUtil.skipWhitespaceAndNL(content);

            if (content.getChar() != ',') {
                if (!content.isBigger()) {
                    throw new FluentParseException("','", content.getChar(), content.getAbsolutePosition());
                }
                break;
            }
            content.increment();
        }
    }

    private Pair<StringSlice, FluentArgument<?>> getArgument() {
        FluentPlaceable<?> placeable = StringSliceUtil.getExpression(content);
        StringSlice identifier = null;

        StringSliceUtil.skipWhitespace(content);

        if (content.getChar() == ':') {
            content.increment();
            StringSliceUtil.skipWhitespace(content);

            identifier = placeable.getContent();

            placeable = StringSliceUtil.getExpression(content);
        }

        return new ImmutablePair<>(identifier, placeable);
    }

    public StringSlice getContent() {
        return this.functionName;
    }

    @Override
    public FluentArgument<?> valueOf() {
        return null;
    }

    @Override
    public boolean matches(FluentArgument<?> selector) {
        return this.equals(selector);
    }

    @Override
    public String stringValue() {
        return this.functionName.toString();
    }

    protected boolean check(StringSlice string) {
        while (!string.isBigger()) {
            if (!Character.isUpperCase(string.getChar())) {
                return false;
            }

            string.increment();
        }

        string.setIndex(0);

        return true;
    }
}
