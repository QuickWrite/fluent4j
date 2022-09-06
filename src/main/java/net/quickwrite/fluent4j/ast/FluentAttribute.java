package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.util.StringSlice;


/**
 * A FluentAttribute defines a subpart of a FluentMessage that holds more
 * context data.<br>
 *
 * <p>
 * <pre>
 * login-input = Predefined value
 *     .placeholder = email@example.com
 *     .aria-label = Login input value
 *     .title = Type your login email
 * </pre>
 * <p>
 * In this example the {@code login-input} has three different attributes:
 * A {@code placeholder} attribute, {@code aria-label} attribute, and a {@code title} attribute.
 * </p>
 */
public class FluentAttribute extends FluentBase {
    /**
     * Creates a new FluentAttribute with the identifier and the content.
     * <br>
     * The content gets parsed into a list of TextElements and Placeables
     * that can be queried later.
     *
     * @param identifier The information that uniquely represents the Attribute.
     * @param content    The content that needs to be parsed.
     */
    public FluentAttribute(final StringSlice identifier, final StringSlice content, final int whitespace) {
        super(identifier, content, whitespace);
    }

    /**
     * Returns a serialized version of the object.
     *
     * @return The object in string form.
     */
    @Override
    public String toString() {
        return "FluentAttribute: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
