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
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class FluentBase implements FluentElement {
    protected final StringSlice identifier;
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
     * @param content The content that needs to be parsed.
     */
    public FluentBase(StringSlice identifier, StringSlice content, int whitespace) {
        this.identifier = identifier;

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

    private FluentTextElement getText(boolean firstLine) {
        if (firstLine && StringSliceUtil.skipWhitespaceAndNL(content)){
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

    private FluentPlaceable getPlaceable() {
        content.increment();
        StringSliceUtil.skipWhitespaceAndNL(content);

        FluentPlaceable placeable = StringSliceUtil.getExpression(content);

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

            while (content.getChar() != '}') {
                fluentVariants.add(getVariant());
            }

            int defaults = 0;
            for (FluentVariant variant : fluentVariants) {
                if (variant.isDefault())
                    defaults++;
            }

            if (defaults == 0) {
                throw new FluentSelectException("Expected one of the variants to be marked as default (*)");
            }
            if (defaults > 1) {
                throw new FluentSelectException("Only one variant can be marked as default (*)");
            }

            placeable = new SelectExpression(placeable, fluentVariants);
        }

        StringSliceUtil.skipWhitespaceAndNL(content);

        if (content.getChar() != '}') {
            throw new FluentParseException('}', content.getChar(), content.getAbsolutePosition());
        }

        content.increment();

        return placeable;
    }

    public StringSlice getIdentifier() {
        return this.identifier;
    }

    private FluentVariant getVariant() {
        StringSliceUtil.skipWhitespaceAndNL(content);

        boolean isDefault = false;

        if (content.getChar() == '*') {
            isDefault = true;
            content.increment();
        }

        if (content.getChar() != '[') {
            throw new FluentParseException('[',  content.getChar(), content.getAbsolutePosition());
        }

        content.increment();

        StringSliceUtil.skipWhitespace(content);

        StringSlice identifier = getVariantIdentifier(content);

        StringSliceUtil.skipWhitespace(content);

        if (content.getChar() != ']') {
            throw getVariantException(identifier.toString(), "]");
        }

        content.increment();

        Pair<StringSlice, Integer> stringSliceContent = FluentParser.getMessageContent(content,
                character -> character == '[' || character == '*' || character == '}');

        return new FluentVariant(
                new FluentAttribute(identifier, stringSliceContent.getLeft(), stringSliceContent.getRight()),
                isDefault
        );
    }

    private StringSlice getVariantIdentifier(final StringSlice content) {
        char character = content.getChar();
        final int start = content.getPosition();

        while(  character != '\0'
                && character != '\n'
                && character != ']'
        ) {
            content.increment();
            character = content.getChar();
        }

        return content.substring(start, content.getPosition());
    }

    public List<FluentElement> getElements() {
        return fluentElements;
    }

    private FluentParseException getVariantException(String prev, String expected) {
        int start = content.getPosition();

        while (content.getChar() != ']' && !content.isBigger()) {
            content.increment();
        }

        return new FluentParseException(expected,
                prev + content.substring(start, content.getPosition()).toString(),
                content.getAbsolutePosition());
    }
}
