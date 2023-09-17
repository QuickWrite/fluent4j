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

public class FluentContentParserGroup implements FluentContentParser {
    private static final IntermediateTextElement NEWLINE_INTERMEDIATE = new IntermediateTextElement(CharBuffer.wrap("\n"), -1, false);
    private final FluentPatternParser<? extends FluentPattern>[] parsers;

    private FluentContentParserGroup(final FluentPatternParser<? extends FluentPattern>[] parsers) {
        this.parsers = parsers;
    }

    @Override
    public List<FluentPattern> parse(final ContentIterator iterator, final EndChecker endChecker) {
        final List<FluentPattern> patternList = generatePatternList(iterator, endChecker);

        return sanitizePatternList(patternList);
    }

    private List<FluentPattern> generatePatternList(final ContentIterator iterator, final EndChecker endChecker) {
        final List<FluentPattern> patternList = new ArrayList<>();

        int textStart = iterator.position()[1];
        boolean isAfterNL = false;

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentPatternParser<? extends FluentPattern> patternParser : parsers) {
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

            if (iterator.character() != '\n') {
                iterator.nextChar();
                continue;
            }

            if (textStart != position[1]) {
                patternList.add(createIntermediateTextElement(iterator, textStart, position, isAfterNL));
            } else if (isAfterNL && patternList.size() != 0) {
                patternList.add((FluentPattern) NEWLINE_INTERMEDIATE);
            }

            iterator.nextChar();
            if (endChecker.check(iterator)) {
                break;
            }

            textStart = 0;
            isAfterNL = true;
        }

        return patternList;
    }

    final List<FluentPattern> sanitizePatternList(final List<FluentPattern> patternList) {
        if (patternList.size() == 0) {
            return List.of();
        }

        int minWhitespace = Integer.MAX_VALUE;

        for (final FluentPattern pattern : patternList) {
            if (!(pattern instanceof IntermediateTextElement textElement)) {
                continue;
            }

            if (!textElement.isAfterNL()) {
                continue;
            }

            final int whitespace = textElement.whitespace();
            if (whitespace != -1 && whitespace < minWhitespace) {
                minWhitespace = whitespace;
            }
        }

        if (minWhitespace == Integer.MAX_VALUE) {
            minWhitespace = 0;
        }

        final FluentContentParserListBuilder builder = new FluentContentParserListBuilder(patternList.size());

        int start = skipLeadingNL(patternList);

        for(int i = start; i < patternList.size(); i++) {
            if (patternList.get(i) instanceof Sanitizable sanitizable) {
                sanitizable.sanitize(i, start, patternList, builder, minWhitespace);
                continue;
            }

            builder.appendElement(patternList.get(i));
        }

        builder.removeTrailingWhitespace();

        builder.flushString();

        return builder.currentList();
    }

    private int countWhitespace(final CharSequence sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            if (!Character.isWhitespace(sequence.charAt(i))) {
                return i;
            }
        }

        return sequence.length();
    }

    private int skipLeadingNL(final List<FluentPattern> patternList) {
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

    public static FluentContentParser.Builder builder() {
        return new FluentContentParserGroupBuilder();
    }

    private static class FluentContentParserGroupBuilder implements FluentContentParser.Builder {
        private final List<FluentPatternParser<? extends FluentPattern>> parserList;

        public FluentContentParserGroupBuilder() {
            this.parserList = new ArrayList<>();
        }

        @Override
        public Builder addParser(final FluentPatternParser<? extends FluentPattern> parser) {
            parserList.add(parser);

            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public FluentContentParser build() {
            return new FluentContentParserGroup(parserList.toArray(new FluentPatternParser[0]));
        }
    }

    private static final class FluentContentParserListBuilder implements ListBuilder {
        private final List<FluentPattern> patternList;
        private final StringBuilder builder;

        public FluentContentParserListBuilder(final int bufferSize) {
            this.patternList = new ArrayList<>(bufferSize);
            this.builder = new StringBuilder();
        }

        @Override
        public List<FluentPattern> currentList() {
            return this.patternList;
        }

        @Override
        public ListBuilder appendString(final CharSequence charSequence) {
            builder.append(charSequence);

            return this;
        }

        @Override
        public ListBuilder appendString(final char character) {
            builder.append(character);

            return this;
        }

        @Override
        public ListBuilder appendElement(final FluentPattern pattern) {
            this.flushString();

            patternList.add(pattern);

            return this;
        }

        @Override
        public void flushString() {
            if (this.builder.isEmpty()) {
                return;
            }

            this.patternList.add(new FluentTextElement(this.builder.toString()));
            this.builder.setLength(0);
        }

        public void removeTrailingWhitespace() {
            for (int i = this.builder.length() - 1; i > -1; i--) {
                if (!Character.isWhitespace(builder.codePointAt(i))) {
                    this.builder.setLength(i + 1);

                    return;
                }
            }

            this.builder.setLength(0);
        }
    }
}
