package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FluentContentParserGroup<B extends ResultBuilder> implements FluentContentParser<B> {
    @SuppressWarnings("unchecked")
    public static final FluentContentParser<? extends ResultBuilder> DEFAULT = builder()
                .addParser((FluentPatternParser<? extends FluentPattern<ResultBuilder>, ResultBuilder>) FluentPlaceableParser.DEFAULT)
                .build();

    private final List<FluentPatternParser<? extends FluentPattern<B>, B>> parserList;

    private static final IntermediateTextElement<?> NEWLINE_INTERMEDIATE = new IntermediateTextElement<>(CharBuffer.wrap("\n"), -1, false);

    private FluentContentParserGroup(final List<FluentPatternParser<? extends FluentPattern<B>, B>> parserList) {
        this.parserList = parserList;
    }

    @Override
    public List<FluentPattern<B>> parse(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker) {
        final List<FluentPattern<B>> patternList = generatePatternList(iterator, endChecker);

        return sanitizePatternList(patternList);
    }

    @SuppressWarnings("unchecked")
    private List<FluentPattern<B>> generatePatternList(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker) {
        final List<FluentPattern<B>> patternList = new ArrayList<>();

        int textStart = iterator.position()[1];
        boolean isAfterNL = false;

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentPatternParser<? extends FluentPattern<B>, B> patternParser : parserList) {
                if (iterator.character() != patternParser.getStartingChar()) {
                    continue;
                }

                final ParseResult<? extends FluentPattern<B>> result = patternParser.parse(iterator, this);

                if (result.getType() == ParseResult.ParseResultType.FAILURE) {
                    iterator.setPosition(position);
                    continue;
                }

                if (textStart != position[1]) {
                    patternList.add(createIntermediateTextElement(iterator, textStart, position, isAfterNL));
                }

                if (result.getType() == ParseResult.ParseResultType.SUCCESS) {
                    patternList.add(result.getValue());
                }

                isAfterNL = false;
                textStart = iterator.position()[1];
                continue outer;
            }

            if (iterator.character() != '\n') {
                iterator.nextChar();
                continue;
            }

            if (textStart != position[1]) {
                patternList.add(createIntermediateTextElement(iterator, textStart, position, isAfterNL));
            } else if (isAfterNL && patternList.size() != 0) {
                patternList.add((FluentPattern<B>) NEWLINE_INTERMEDIATE);
            }

            iterator.nextChar();
            if (endChecker.apply(iterator)) {
                break;
            }

            textStart = 0;
            isAfterNL = true;
        }

        return patternList;
    }

    final List<FluentPattern<B>> sanitizePatternList(final List<FluentPattern<B>> patternList) {
        int minWhitespace = Integer.MAX_VALUE;

        for (final FluentPattern<B> pattern : patternList) {
            if (!(pattern instanceof IntermediateTextElement)) {
                continue;
            }

            final IntermediateTextElement<?> textElement = (IntermediateTextElement<?>) pattern;

            if (!textElement.isAfterNL()) {
                continue;
            }

            final int whitespace = textElement.getWhitespace();
            if (whitespace != -1 && whitespace < minWhitespace) {
                minWhitespace = whitespace;
            }
        }

        if (minWhitespace == Integer.MAX_VALUE) {
            minWhitespace = 0;
        }

        if (patternList.size() == 0) {
            return List.of();
        }

        final List<FluentPattern<B>> result = new ArrayList<>(patternList.size());
        final StringBuilder builder = new StringBuilder();
        int start = skipLeadingNL(patternList);

        firstElementIf:
        if (start == 0 && patternList.get(0) instanceof IntermediateTextElement) {
            start = 1;

            final IntermediateTextElement<?> textElement = (IntermediateTextElement<?>) patternList.get(0);

            if (textElement.getWhitespace() == -1) {
                break firstElementIf;
            }

            builder.append(
                    textElement.slice(
                            // If there was something before this just use the calculated whitespace
                            textElement.isAfterNL() ? minWhitespace : textElement.getWhitespace()
                    )
            );
        }

        for (int i = start; i < patternList.size(); i++) {
            final FluentPattern<B> element = patternList.get(i);

            if (!(element instanceof IntermediateTextElement)) {
                 if (builder.length() != 0) {
                    result.add(new FluentTextElement<>(builder.toString()));

                    // clear the StringBuilder
                    builder.setLength(0);
                }

                result.add(patternList.get(i));

                continue;
            }

            final IntermediateTextElement<?> textElement = (IntermediateTextElement<?>) patternList.get(i);

            if (!textElement.isAfterNL()) {
                builder.append(textElement.getContent());

                continue;
            }

            builder.append('\n');

            builder.append(textElement.slice(minWhitespace));
        }

        removeTrailingWhitespace(builder);

        if (builder.length() != 0) {
            result.add(new FluentTextElement<>(builder.toString()));
        }

        return result;
    }

    private int countWhitespace(final CharSequence sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            if (!Character.isWhitespace(sequence.charAt(i))) {
                return i;
            }
        }

        return sequence.length();
    }

    private int skipLeadingNL(final List<FluentPattern<B>> patternList) {
        for (int i = 0; i < patternList.size(); i++) {
            if (!(patternList.get(i) instanceof IntermediateTextElement)) {
                return i;
            }

            if (patternList.get(i) != NEWLINE_INTERMEDIATE) {
                return i;
            }
        }

        return patternList.size() - 1;
    }

    private void removeTrailingWhitespace(final StringBuilder builder) {
        for (int i = builder.length() - 1; i > -1; i--) {
            if (!Character.isWhitespace(builder.codePointAt(i))) {
                builder.setLength(i + 1);

                return;
            }
        }

        builder.setLength(0);
    }

    private IntermediateTextElement<B> createIntermediateTextElement(
            final ContentIterator iterator,
            final int start,
            final int[] end,
            final boolean isAfterNL
    ) {
        final int[] currentPos = iterator.position();

        iterator.setPosition(end);

        final CharBuffer buffer = CharBuffer.wrap(iterator.line(), start, end[1]);
        final IntermediateTextElement<B> textElement = new IntermediateTextElement<>(
                buffer,
                countWhitespace(buffer),
                isAfterNL
        );
        iterator.setPosition(currentPos);

        return textElement;
    }


    public static <B extends ResultBuilder> FluentContentParser.Builder<B> builder() {
        return new FluentContentParserGroupBuilder<>();
    }

    private static class FluentContentParserGroupBuilder<B extends ResultBuilder> implements FluentContentParser.Builder<B> {
        private final List<FluentPatternParser<? extends FluentPattern<B>, B>> parserList;

        public FluentContentParserGroupBuilder() {
            this.parserList = new ArrayList<>();
        }

        @Override
        public Builder<B> addParser(final FluentPatternParser<? extends FluentPattern<B>, B> parser) {
            parserList.add(parser);

            return this;
        }

        @Override
        public FluentContentParser<B> build() {
            return new FluentContentParserGroup<>(parserList);
        }
    }
}
