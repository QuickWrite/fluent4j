package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class FluentContentParserGroup implements FluentContentParser {
    private final List<FluentPatternParser<? extends FluentPattern>> patternParserList = new ArrayList<>();

    public static FluentContentParserGroup getBasicParser() {
        final FluentContentParserGroup group = new FluentContentParserGroup();

        return group;
    }

    public void addParser(final FluentPatternParser<? extends FluentPattern> parser) {
        this.patternParserList.add(parser);
    }

    @Override
    public List<FluentPattern> parse(final ContentIterator iterator) {
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

                patternList.add(createIntermediateTextElement(iterator, textStart, position, isAfterNL));

                if (result.getType() == ParseResult.ParseResultType.SUCCESS) {
                    patternList.add(result.getValue());
                }

                isAfterNL = false;
                textStart = iterator.position()[1];
                continue outer;
            }

            if (iterator.character() == '\n') {
                patternList.add(createIntermediateTextElement(iterator, textStart, position, isAfterNL));

                textStart = 0;
                isAfterNL = true;
            }

            iterator.nextChar();
        }

        return patternList;
    }

    private IntermediateTextElement createIntermediateTextElement(
            final ContentIterator iterator,
            final int start,
            final int[] end,
            final boolean isAfterNL
    ) {
        final int[] currentPos = iterator.position();

        iterator.setPosition(end);
        final IntermediateTextElement textElement = new IntermediateTextElement(
                CharBuffer.wrap(iterator.line(), start, end[1]),
                isAfterNL
        );
        iterator.setPosition(currentPos);

        return textElement;
    }
}
