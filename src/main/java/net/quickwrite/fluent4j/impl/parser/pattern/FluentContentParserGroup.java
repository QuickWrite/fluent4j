package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FluentContentParserGroup implements FluentContentParser {
    private final List<FluentPatternParser<? extends FluentPattern>> patternParserList = new ArrayList<>();

    public static FluentContentParserGroup getBasicParser() {
        final FluentContentParserGroup group = new FluentContentParserGroup();

        group.addParser(FluentPatternParser.DEFAULT_PLACEABLE_PARSER);

        return group;
    }

    public void addParser(final FluentPatternParser<? extends FluentPattern> parser) {
        this.patternParserList.add(parser);
    }

    @Override
    public List<FluentPattern> parse(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker) {
        final List<FluentPattern> patternList = generatePatternList(iterator, endChecker);

        return sanitizePatternList(patternList);
    }

    private List<FluentPattern> generatePatternList(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker) {
        final List<FluentPattern> patternList = new ArrayList<>();

        int textStart = iterator.position()[1];
        boolean isAfterNL = false;

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentPatternParser<? extends FluentPattern> patternParser : patternParserList) {
                if (iterator.character() != patternParser.getStartingChar()) {
                    continue;
                }

                final ParseResult<? extends FluentPattern> result = patternParser.parse(iterator, this);

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

            if (iterator.character() == '\n') {
                if (textStart != position[1]) {
                    patternList.add(createIntermediateTextElement(iterator, textStart, position, isAfterNL));
                }

                iterator.nextChar();
                if (endChecker.apply(iterator)) {
                    break;
                }

                textStart = 0;
                isAfterNL = true;

                continue;
            }

            iterator.nextChar();
        }

        return patternList;
    }

    final List<FluentPattern> sanitizePatternList(final List<FluentPattern> patternList) {
        int minWhitespace = Integer.MAX_VALUE;

        for (final FluentPattern pattern : patternList) {
            if (!(pattern instanceof IntermediateTextElement)) {
                continue;
            }

            final IntermediateTextElement textElement = (IntermediateTextElement) pattern;

            if (!textElement.isAfterNL()) {
                continue;
            }

            final int whitespace = textElement.getWhitespace();
            if (whitespace != -1 && whitespace < minWhitespace) {
                minWhitespace = whitespace;
            }
        }

        final List<FluentPattern> result = new ArrayList<>(patternList.size());
        final StringBuilder builder = new StringBuilder();
        int start = 0;

        firstElementIf:
        if (patternList.get(0) instanceof IntermediateTextElement) {
            start = 1;

            final IntermediateTextElement textElement = (IntermediateTextElement) patternList.get(0);

            if (textElement.getWhitespace() == -1) {
                break firstElementIf;
            }

            builder.append(
                    slice(textElement.getContent(), textElement.getWhitespace(), textElement.getContent().length())
            );
        }

        for (int i = start; i < patternList.size(); i++) {
            final FluentPattern element = patternList.get(i);

            if (!(element instanceof IntermediateTextElement)) {
                 if (builder.length() != 0) {
                    result.add(new FluentTextElement(builder.toString()));

                    // clear the StringBuilder
                    builder.setLength(0);
                }

                result.add(patternList.get(i));

                continue;
            }

            final IntermediateTextElement textElement = (IntermediateTextElement) patternList.get(i);

            if (textElement.isAfterNL()) {
                builder.append('\n');
                builder.append(
                        slice(textElement.getContent(), minWhitespace, textElement.getContent().length())
                );
                continue;
            }

            builder.append(textElement.getContent());
        }

        removeTrailingWhitespace(builder);

        if (builder.length() != 0) {
            result.add(new FluentTextElement(builder.toString()));
        }

        return result;
    }

    private int countWhitespace(final CharSequence sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            if (!Character.isWhitespace(sequence.charAt(i))) {
                return i;
            }
        }

        return -1;
    }

    private CharBuffer slice(final CharBuffer base, final int start, final int end) {
        return base.subSequence(start, end);
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

    private IntermediateTextElement createIntermediateTextElement(
            final ContentIterator iterator,
            final int start,
            final int[] end,
            final boolean isAfterNL
    ) {
        final int[] currentPos = iterator.position();

        iterator.setPosition(end);

        final CharBuffer buffer = CharBuffer.wrap(iterator.line(), start, end[1]);
        final IntermediateTextElement textElement = new IntermediateTextElement(
                buffer,
                countWhitespace(buffer),
                isAfterNL
        );
        iterator.setPosition(currentPos);

        return textElement;
    }
}
