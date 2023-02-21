package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.exception.FluentBuilderException;
import net.quickwrite.fluent4j.container.exception.FluentExpectedException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentSelectExpression;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.*;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluentPlaceableParser<B extends ResultBuilder> implements PlaceableParser<B> {
    private final List<PlaceableExpressionParser<?, B>> expressionParserList = new ArrayList<>();

    public static FluentPlaceableParser<ResultBuilder> getBasicParser() {
        final FluentPlaceableParser<ResultBuilder> base = new FluentPlaceableParser<>();

        base.addParser(new FluentStringLiteralParser<>());
        base.addParser(new FluentNumberLiteralParser<>());
        base.addParser(new FluentFunctionParser<>());

        base.addParser(new FluentTermReferenceParser<>());
        base.addParser(new FluentMessageReferenceParser<>());

        base.addParser(new FluentVariableReferenceParser<>());

        return base;
    }

    public void addParser(final PlaceableExpressionParser<?, B> parser) {
        this.expressionParserList.add(parser);
    }

    @Override
    public int getStartingChar() {
        return '{';
    }

    @Override
    public ParseResult<FluentPattern<B>> parse(final ContentIterator iterator, final FluentContentParser<B> contentParser) {
        iterator.nextChar();

        ParserUtil.skipWhitespaceAndNL(iterator);

        final FluentPlaceable<B> placeable = parsePlaceable(iterator)
                .orElseThrow(() -> new FluentBuilderException("All PlaceableExpressionParsers returned FAILURE", iterator));

        select:
        if (placeable instanceof FluentSelect.Selectable) {
            ParserUtil.skipWhitespace(iterator);

            final Optional<FluentSelect<B>> selectExpression = parseSelector(
                    iterator,
                    contentParser,
                    (FluentSelect.Selectable<B>) placeable
            );

            if (selectExpression.isEmpty()) {
                break select;
            }

            return ParseResult.success(selectExpression.get());
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        if (iterator.character() != '}') {
            throw new FluentExpectedException('}', iterator);
        }

        if (placeable instanceof FluentPlaceable.CannotPlaceable) {
            throw new RuntimeException(
                    "A " +
                    ((FluentPlaceable.CannotPlaceable) placeable).getName() +
                    " cannot be used as a placeable."
            );
        }

        iterator.nextChar();

        return ParseResult.success(placeable);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<FluentPlaceable<B>> parsePlaceable(final ContentIterator iterator) {
        final int[] position = iterator.position();

        for (final PlaceableExpressionParser<?, B> expressionParser : expressionParserList) {
            final Optional<?> parseResult = expressionParser.parse(iterator, this);

            if (parseResult.isEmpty()) {
                iterator.setPosition(position);
                continue;
            }

            return (Optional<FluentPlaceable<B>>) parseResult;
        }

        return Optional.empty();
    }

    private Optional<FluentSelect<B>> parseSelector(
            final ContentIterator iterator,
            final FluentContentParser<B> contentParser,
            FluentSelect.Selectable<B> selectable
    ) {
        if (iterator.character() != '-') {
            return Optional.empty();
        }

        if (iterator.nextChar() != '>') {
            throw new FluentExpectedException("->", "-" + iterator.character(), iterator);
        }

        iterator.nextChar();

        ParserUtil.skipWhitespace(iterator);

        if (iterator.character() != '\n') {
            throw new FluentExpectedException('\n', iterator);
        }

        ParserUtil.skipWhitespaceAndNL(iterator);

        final List<FluentSelect.FluentVariant<B>> variantList = new ArrayList<>();
        FluentSelect.FluentVariant<B> defaultVariant = null;

        while (true) {
            boolean isDefault = false;

            if (iterator.character() == '*') {
                if (defaultVariant != null) {
                    throw new FluentBuilderException("Only one default variant can exist.", iterator);
                }

                isDefault = true;

                iterator.nextChar();
            }

            final Optional<FluentSelect.FluentVariant<B>> variant = parseVariant(iterator, contentParser);

            if (variant.isEmpty()) {
                if (variantList.size() == 0) {
                    throw new FluentBuilderException("Expected at least one variant after \"->\"", iterator);
                }

                // If the default variant is the last variant it can easily be removed
                // as it will always be checked last and so does not need to be in the list
                // of the normal variants.
                if (defaultVariant == variantList.get(variantList.size() - 1)) {
                    variantList.remove(variantList.size() - 1);
                }

                break;
            }

            variantList.add(variant.get());

            if (isDefault) {
                defaultVariant = variant.get();
            }
        }

        if (defaultVariant == null) {
            // TODO: Better error position
            throw new FluentBuilderException("Expected at least one variant to be marked as default (*)", iterator);
        }

        iterator.nextChar();

        System.out.println("Variants:" + variantList);
        System.out.println("Default-Variant: " + defaultVariant);

        if (selectable instanceof IntermediateTextElement) {
            selectable = new FluentTextElement<>(((IntermediateTextElement<B>) selectable).getContent().toString());
        }

        return Optional.of(new FluentSelectExpression<>(selectable, variantList, defaultVariant));
    }

    private Optional<FluentSelect.FluentVariant<B>> parseVariant(final ContentIterator iterator, final FluentContentParser<B> contentParser) {
        if (iterator.character() != '[') {
            return Optional.empty();
        }

        iterator.nextChar();

        ParserUtil.skipWhitespace(iterator);

        final FluentSelect.FluentVariant.FluentVariantKey<B> variantKey = getVariantKey(iterator);

        ParserUtil.skipWhitespace(iterator);

        if (iterator.character() != ']') {
            throw new FluentExpectedException(']', iterator);
        }

        iterator.nextChar();

        final List<FluentPattern<B>> content = contentParser.parse(iterator, contentIterator -> {
            ParserUtil.skipWhitespace(contentIterator);

            final int character = contentIterator.character();
            return character == '[' || character == '*' || character == '}';
        });

        return Optional.of(new FluentSelectExpression.FluentVariant<>(variantKey, content));
    }

    private FluentSelect.FluentVariant.FluentVariantKey<B> getVariantKey(final ContentIterator iterator) {
        final Optional<String> number = FluentNumberLiteralParser.parseNumberLiteral(iterator);
        if (number.isPresent()) {
            return new FluentNumberLiteral<>(number.get());
        }
        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);

        if (identifier.isEmpty()) {
            throw new FluentBuilderException("Expected identifier", iterator);
        }

        return new FluentTextElement<>(identifier.get());
    }

}
