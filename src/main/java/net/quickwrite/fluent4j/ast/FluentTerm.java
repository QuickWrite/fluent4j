package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.util.StringSlice;

import java.util.List;

/**
 * Terms are similar to regular messages but they can
 * only be used as references in other messages. Their
 * identifiers start with a single dash {@code -}
 * like in the example: {@code -brand-name}. The
 * runtime cannot retrieve terms directly. They are best
 * used to define vocabulary and glossary items which
 * can be used consistently across the localization
 * of the entire product.
 *
 * <pre>
 *     -brand-name = Firefox
 *
 *     about = About { -brand-name }.
 *     update-successful = { -brand-name } has been updated.
 * </pre>
 */
public class FluentTerm extends FluentMessage {

    /**
     * Creates a new FluentTerm with the identifier, content and a list
     * of FluentAttributes.
     * <br>
     * The content gets parsed into a list of TextElements and Placeables
     * that can be queried later.
     *
     * @param identifier The information that uniquely represents the Attribute.
     * @param content    The content that needs to be parsed.
     * @param attributes All of the attributes
     */
    public FluentTerm(final StringSlice identifier,
                      final StringSlice content,
                      final List<FluentAttribute> attributes,
                      final int whitespace) {
        super(identifier, content, attributes, whitespace);
    }

    @Override
    public String toString() {
        return "FluentTerm: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tattributes: " + this.attributes + "\n" +
                "\t\t\tfluentElements: " + this.fluentElements + "\n" +
                "\t\t}";
    }
}
