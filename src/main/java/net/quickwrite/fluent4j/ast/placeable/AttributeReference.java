package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

/**
 * Can reference the Attributes of a Fluent Message or Term.
 * <br>
 * It can be declared like this:
 * <pre>
 * message-test = { message.attribute }
 * -term-test = { -term.attribute }
 * </pre>
 */
public class AttributeReference implements FluentPlaceable<FluentPlaceable<?>> {
    private final FluentPlaceable<?> reference;
    private final StringSlice content;

    public AttributeReference(FluentPlaceable<?> reference, StringSlice content) {
        this.reference = reference;
        this.content = content;
    }

    @Override
    public StringSlice getContent() {
        return null;
    }

    @Override
    public FluentPlaceable<?> valueOf() {
        return this.reference;
    }

    @Override
    public boolean matches(FluentArgument<?> selector) {
        return false;
    }

    @Override
    public String stringValue() {
        return this.reference.stringValue();
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        // TODO: Implement
        return null;
    }

    @Override
    public String toString() {
        return "FluentAttributeReference: {\n" +
                "\t\t\tvalue: \"" + this.reference + "\"\n" +
                "\t\t\tattribute: \"" + this.content + "\"\n" +
                "\t\t}";
    }

    public static class TermAttributeReference extends AttributeReference implements FluentSelectable {
        public TermAttributeReference(FluentPlaceable<?> reference, StringSlice content) {
            super(reference, content);
        }
    }
}