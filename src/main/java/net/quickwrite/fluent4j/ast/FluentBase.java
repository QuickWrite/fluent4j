package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.FluentBundle;
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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class FluentBase implements FluentElement {
    protected final String identifier;
    protected final int whitespace;

    protected final List<FluentElement> fluentElements;
    protected StringSlice content;

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
        this.content = content;
        this.whitespace = whitespace;

        this.fluentElements = parse();
    }

    private List<FluentElement> parse() {
        List<FluentElement> elements = new LinkedList<>();

        StringSliceUtil.skipWhitespace(content);
        boolean firstLine = true;

        while (!content.isBigger()) {
            if (content.getChar() == '{') {
                elements.add(getPlaceable());
            } else {
                FluentTextElement text = getText(firstLine);

                if (text != null) {
                    elements.add(text);
                }
            }

            firstLine = false;
        }

        return elements;
    }

    private FluentTextElement getText(final boolean firstLine) {
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

    private FluentPlaceable<?> getPlaceable() {
        content.increment();
        StringSliceUtil.skipWhitespaceAndNL(content);

        FluentPlaceable<?> placeable = StringSliceUtil.getExpression(content);

        boolean canSelect = placeable instanceof FluentSelectable;

        if (!canSelect && content.getChar() == '.') {
            content.increment();
            StringSlice slice = StringSliceUtil.getIdentifier(content);

            if (placeable instanceof TermReference) {
                placeable = new AttributeReference.TermAttributeReference(placeable, slice);
            } else {
                placeable = new AttributeReference(placeable, slice);
            }

            canSelect = true;
        }

        StringSliceUtil.skipWhitespaceAndNL(content);

        if (canSelect && content.getChar() == '-') {
            content.increment();
            if (content.getChar() != '>') {
                throw new FluentParseException("->", "-" + content.getChar(), content.getAbsolutePosition());
            }

            content.increment();

            StringSliceUtil.skipWhitespaceAndNL(content);

            List<FluentVariant> fluentVariants = new ArrayList<>();
            FluentVariant defaultVariant = null;

            while (content.getChar() != '}') {
                Pair<FluentVariant, Boolean> variant = getVariant();

                fluentVariants.add(variant.getLeft());

                if (!variant.getRight()) {
                    continue;
                }

                if (defaultVariant != null) {
                    throw new FluentSelectException("Only one variant can be marked as default (*)");
                }

                defaultVariant = variant.getLeft();
            }

            if (defaultVariant == null) {
                throw new FluentSelectException("Expected one of the variants to be marked as default (*)");
            }

            placeable = new SelectExpression(placeable, fluentVariants, defaultVariant);
        }

        StringSliceUtil.skipWhitespaceAndNL(content);

        if (content.getChar() != '}') {
            throw new FluentParseException('}', content.getChar(), content.getAbsolutePosition());
        }

        content.increment();

        return placeable;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    private Pair<FluentVariant, Boolean> getVariant() {
        StringSliceUtil.skipWhitespaceAndNL(content);

        boolean isDefault = false;

        if (content.getChar() == '*') {
            isDefault = true;
            content.increment();
        }

        if (content.getChar() != '[') {
            throw new FluentParseException('[', content.getChar(), content.getAbsolutePosition());
        }

        content.increment();

        StringSliceUtil.skipWhitespace(content);

        StringSlice identifier = getVariantIdentifier(content);

        StringSliceUtil.skipWhitespace(content);

        if (content.getChar() != ']') {
            throw getVariantException(identifier.toString(), "]");
        }

        content.increment();

        final Pair<StringSlice, Integer> stringSliceContent = FluentParser.getMessageContent(content,
                character -> character == '[' || character == '*' || character == '}');

        final FluentAttribute attribute = new FluentAttribute(
                identifier,
                stringSliceContent.getLeft(),
                stringSliceContent.getRight()
        );

        return new ImmutablePair<>(new FluentVariant(attribute), isDefault);
    }

    private StringSlice getVariantIdentifier(final StringSlice content) {
        char character = content.getChar();
        final int start = content.getPosition();

        while (character != '\0'
                && character != '\n'
                && character != ']'
        ) {
            content.increment();
            character = content.getChar();
        }

        return content.substring(start, content.getPosition());
    }

    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        StringBuilder builder = new StringBuilder();

        for (FluentElement element : this.fluentElements) {
            if (element instanceof FluentTextElement) {
                builder.append(((FluentTextElement) element).valueOf());
            } else {
                builder.append(((FluentPlaceable<?>) element).getResult(bundle, arguments));
            }
        }

        return builder.toString();
    }

    public List<FluentElement> getElements() {
        return this.fluentElements;
    }

    private FluentParseException getVariantException(final String prev, final String expected) {
        int start = content.getPosition();

        while (content.getChar() != ']' && !content.isBigger()) {
            content.increment();
        }

        return new FluentParseException(expected,
                prev + content.substring(start, content.getPosition()).toString(),
                content.getAbsolutePosition());
    }
}