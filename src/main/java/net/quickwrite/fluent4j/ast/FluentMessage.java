package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.util.StringSlice;

import java.util.List;

/**
 * In Fluent, the basic unit of translation is called a message.
 * Messages are containers for information. You use messages to
 * identify, store, and recall translation information to be used
 * in the product. The simplest example of a message looks like this:
 * <pre>
 *     hello = Hello, world!
 * </pre>
 *
 * <p>
 *     Each message has an identifier that allows the developer to bind
 *     it to the place in the software where it will be used.
 *     The above message is called {@code hello}.
 * </p>
 *
 */
public class FluentMessage extends FluentBase {
    protected List<FluentAttribute> attributes;

    /**
     * Creates a new FluentMessage with the identifier, content and a list
     * of FluentAttributes.
     * <br>
     * The content gets parsed into a list of TextElements and Placeables
     * that can be queried later.
     *
     * @param identifier The information that uniquely represents the Attribute.
     * @param content The content that needs to be parsed.
     * @param attributes All of the attributes
     */
    public FluentMessage(final StringSlice identifier,
                         final StringSlice content,
                         final List<FluentAttribute> attributes,
                         final int whitespace) {
        super(identifier, content, whitespace);

        this.attributes = attributes;
    }

    public FluentAttribute getAttribute(final String identifier) {
        for(final FluentAttribute attribute : this.attributes) {
            if (attribute.getIdentifier().equals(identifier)) {
                return attribute;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "FluentMessage: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tattributes: " + this.attributes + "\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
