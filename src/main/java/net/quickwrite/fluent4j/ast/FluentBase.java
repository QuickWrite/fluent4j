package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.placeable.AttributeReference;
import net.quickwrite.fluent4j.ast.placeable.SelectExpression;
import net.quickwrite.fluent4j.ast.placeable.TermReference;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.exception.FluentSelectException;
import net.quickwrite.fluent4j.parser.FluentParser;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.StringSliceUtil;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class FluentBase implements FluentElement {
    protected final String identifier;
    protected final int whitespace;

    protected final List<FluentElement> fluentElements;

    /**
     * Creates a new FluentAttribute with the identifier and the content.
     * <br>
     * The content gets parsed into a list of TextElements and Placeables
     * that can be queried later.
     *
     * @param identifier The information that uniquely represents the Attribute.
     * @param content    The content that needs to be parsed.
     */
    public FluentBase(final StringSlice identifier,
                      final StringSlice content,
                      final int whitespace) {
        this.identifier = identifier.toString();
        this.whitespace = whitespace;

        this.fluentElements = parse(content);
    }

    private List<FluentElement> parse(final StringSlice content) {
        final List<FluentElement> elements = new LinkedList<>();

        StringSliceUtil.skipWhitespace(content);
        boolean firstLine = true;

        while (!content.isBigger()) {
            if (content.getChar() == '{') {
                elements.add(FluentParser.getPlaceable(content));

                if (content.getChar() != '}') {
                    throw new FluentParseException('}', content.getCharUTF16(), content.getAbsolutePosition());
                }
                content.increment();
            } else {
                FluentTextElement text = getText(content, firstLine);

                if (text != null && !text.isEmpty()) {
                    elements.add(text);
                }
            }

            firstLine = false;
        }

        return elements;
    }

    private FluentTextElement getText(final StringSlice content, final boolean firstLine) {
        if (firstLine && StringSliceUtil.skipWhitespaceAndNL(content)) {
            while (content.getChar() != '\n') {
                content.decrement();
            }
            content.increment();
            content.setIndex(content.getPosition() + whitespace);
        }

        final int start = content.getPosition();

        while (!content.isBigger() && content.getChar() != '{') {
            content.increment();
        }

        if (start == content.getPosition()) {
            return null;
        }

        return new FluentTextElement(content.substring(start, content.getPosition()), whitespace);
    }

    public String getIdentifier() {
        return this.identifier;
    }

    private StringSlice getVariantIdentifier(final StringSlice content) {
        char character = content.getChar();
        final int start = content.getPosition();

        while (character != ' '
                && character != '\n'
                && character != ']'
                && character != '\0'
        ) {
            content.increment();
            character = content.getChar();
        }

        return content.substring(start, content.getPosition());
    }

    @Override
    public CharSequence getResult(final DirectFluentBundle bundle, final FluentArgs arguments) {
        if (this.fluentElements.size() == 1) {
            return this.fluentElements.get(0).getResult(bundle, arguments);
        }

        final StringBuilder builder = new StringBuilder();

        for (final FluentElement element : this.fluentElements) {
            builder.append(element.getResult(bundle, arguments));
        }

        return builder;
    }

    @Override
    public boolean matches(DirectFluentBundle bundle, FluentElement selector) {
        return false;
    }

    @Override
    public String stringValue() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final FluentElement element : getElements()) {
            stringBuilder.append(element.stringValue());
        }

        return stringBuilder.toString();
    }

    public List<FluentElement> getElements() {
        return this.fluentElements;
    }
}
