package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.ast.wrapper.FluentArgument;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class FluentFunction implements FluentPlaceable {
    protected final StringSlice functionName;
    protected final StringSlice content;
    protected List<FluentArgument> positionalArgumentList;
    protected List<FluentArgument> namedArgumentList;

    public FluentFunction(StringSlice functionName, StringSlice content) {
        this.functionName = functionName;
        if (!check(functionName)) {
            // TODO: Better Error handling
            throw new FluentParseException("The callee has to be an upper-case identifier or a term");
        }

        this.content = content;

        this.positionalArgumentList = new ArrayList<>();
        this.namedArgumentList = new ArrayList<>();

        if (content != null) {
            getArguments();
        }
    }

    private void getArguments() {
        while (!content.isBigger()) {
            StringSliceUtil.skipWhitespace(content);

            FluentArgument argument = getArgument();
            if (argument.isNamed()) {
                this.namedArgumentList.add(argument);
            } else {
                this.positionalArgumentList.add(argument);
            }

            StringSliceUtil.skipWhitespace(content);

            if (content.getChar() != ',') {
                if (!content.isBigger()) {
                    throw new FluentParseException("','", content.getChar(), content.getPosition());
                }
                break;
            }
            content.increment();
        }
    }

    private FluentArgument getArgument() {
        FluentPlaceable placeable = StringSliceUtil.getExpression(content);
        StringSlice identifier = null;

        StringSliceUtil.skipWhitespace(content);

        if (content.getChar() == ':') {
            content.increment();
            StringSliceUtil.skipWhitespace(content);

            identifier = placeable.getContent();

            placeable = StringSliceUtil.getExpression(content);
        }

        return new FluentArgument(identifier, placeable);
    }

    public StringSlice getContent() {
        return this.content;
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
