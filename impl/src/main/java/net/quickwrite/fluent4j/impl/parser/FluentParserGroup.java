package net.quickwrite.fluent4j.impl.parser;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.impl.container.FluentEntryResource;
import net.quickwrite.fluent4j.impl.parser.base.ElementParserList;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.ResourceParser;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;

public final class FluentParserGroup implements ResourceParser {
    public static ResourceParser DEFAULT = builder()
            .addParser(ElementParserList.WHITESPACE_SKIPPER)
            .addParser(ElementParserList.COMMENT_SKIPPER)
            .addParser(ElementParserList.TERM_PARSER)
            .addParser(ElementParserList.MESSAGE_PARSER)
            .build();

    private final List<FluentElementParser<? extends FluentEntry>> parserList;

    private FluentParserGroup(final List<FluentElementParser<? extends FluentEntry>> parserList) {
        this.parserList = parserList;
    }

    @Override
    public FluentResource parse(final ContentIterator iterator) {
        final List<FluentEntry> elements = new ArrayList<>();

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentElementParser<? extends FluentEntry> parser : parserList) {
                final ParseResult<? extends FluentEntry> result = parser.parse(iterator);

                switch (result.getType()) {
                    case SUCCESS:
                        elements.add(result.getValue());
                    case SKIP:
                        continue outer;
                }

                iterator.setPosition(position);
            }

            // TODO: Something went wrong and it must be handled
            throw new FluentBuilderException("Every parser returned FAILURE.", iterator);
        }

        return new FluentEntryResource(elements);
    }


    public static ResourceParser.Builder builder() {
        return new FluentParserGroupBuilder();
    }

    private static class FluentParserGroupBuilder implements ResourceParser.Builder {
        private final List<FluentElementParser<? extends FluentEntry>> parserList;

        public FluentParserGroupBuilder() {
            this.parserList = new ArrayList<>();
        }

        @Override
        public Builder addParser(final FluentElementParser<? extends FluentEntry> parser) {
            this.parserList.add(parser);

            return this;
        }

        @Override
        public ResourceParser build() {
            return new FluentParserGroup(this.parserList);
        }
    }
}
