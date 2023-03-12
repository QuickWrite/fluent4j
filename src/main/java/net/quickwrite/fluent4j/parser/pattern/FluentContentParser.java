package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.parser.pattern.FluentContentParserGroup;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.util.Builder;

import java.util.List;
import java.util.function.Function;

public interface FluentContentParser<B extends ResultBuilder> {
    FluentContentParser<ResultBuilder> DEFAULT_PARSER = FluentContentParserGroup.getBasicParser();

    static <B extends ResultBuilder> Builder<B> builder() {
        return FluentContentParserGroup.builder();
    }

    List<FluentPattern<B>> parse(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker);

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<FluentContentParser<B>> {
        Builder<B> addParser(final FluentPatternParser<? extends FluentPattern<B>, B> parser);
    }
}
