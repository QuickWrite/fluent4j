package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.function.Function;

public interface FluentContentParser<B extends ResultBuilder> {
    List<FluentPattern<B>> parse(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker);

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<FluentContentParser<B>> {
        Builder<B> addParser(final FluentPatternParser<? extends FluentPattern<B>, B> parser);
    }
}
