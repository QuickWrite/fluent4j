package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.FluentAttribute;
import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

import java.util.List;

/**
 * Can reference the Attributes of a Fluent Message or Term.
 * <br>
 * It can be declared like this:
 * <pre>
 * message-test = { message.attribute }
 * -term-test = { -term.attribute }
 * </pre>
 */
public class AttributeReference implements FluentPlaceable, FluentArgumentResult {
    protected final FluentPlaceable reference;
    protected final String attributeIdentifier;

    public AttributeReference(FluentPlaceable reference, StringSlice attributeIdentifier) {
        this.reference = reference;
        this.attributeIdentifier = attributeIdentifier.toString();
    }

    @Override
    public boolean matches(final FluentBundle bundle, final FluentArgument selector) {
        return false;
    }

    @Override
    public String stringValue() {
        return this.reference.stringValue();
    }

    @Override
    public CharSequence getResult(final FluentBundle bundle, final FluentArgs arguments) {
        final FluentMessage fluentMessage = this.getMessage(bundle, reference.stringValue());
        if (fluentMessage == null) {
            return getErrorString();
        }

        final FluentAttribute attribute = fluentMessage.getAttribute(this.attributeIdentifier);

        if (attribute == null) {
            return getErrorString();
        }

        return attribute.getResult(bundle, arguments);
    }

    @Override
    public FluentArgument getArgumentResult(final FluentBundle bundle, final FluentArgs arguments) {
        final FluentAttribute attribute = this.getMessage(bundle, reference.stringValue())
                .getAttribute(this.attributeIdentifier);
        if (attribute == null) {
            return this;
        }

        final List<FluentElement> elementList = attribute.getElements();

        if (elementList.size() != 1) {
            return this;
        }

        // No recursion (unfortunately :d)
        return (FluentArgument)elementList.get(0);
    }

    protected FluentMessage getMessage(final FluentBundle bundle, final String key) {
        return bundle.getMessage(key);
    }

    protected String getErrorString() {
        return "{" + reference.stringValue() + "." + attributeIdentifier + "}";
    }

    @Override
    public String toString() {
        return "FluentAttributeReference: {\n" +
                "\t\t\tvalue: \"" + this.reference + "\"\n" +
                "\t\t\tattribute: \"" + this.attributeIdentifier + "\"\n" +
                "\t\t}";
    }

    public static class TermAttributeReference extends AttributeReference implements FluentSelectable {
        public TermAttributeReference(FluentPlaceable reference, StringSlice content) {
            super(reference, content);
        }

        @Override
        protected FluentMessage getMessage(final FluentBundle bundle, final String key) {
            return bundle.getTerm(key);
        }

        @Override
        protected String getErrorString() {
            return "{-" + reference.stringValue() + "." + attributeIdentifier + "}";
        }
    }
}