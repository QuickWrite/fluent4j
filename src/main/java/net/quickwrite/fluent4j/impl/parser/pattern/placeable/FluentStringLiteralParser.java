package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public class FluentStringLiteralParser implements PlaceableExpressionParser<FluentTextElement> {
    @Override
    public ParseResult<FluentTextElement> parse(final ContentIterator iterator) {
        if(iterator.character() != '"') {
            return ParseResult.failure();
        }

        iterator.nextChar();

        final int position = iterator.position()[1];

        while (iterator.character() != '"') {
            if (iterator.character() == '\n') {
                throw new RuntimeException("StringLiteral cannot have a \\n");
            }

            iterator.nextChar();
        }

        iterator.nextChar();

        return ParseResult.success(
                new FluentTextElement(
                        iterator.line().substring(position, iterator.position()[1] - 1)
                )
        );
    }

    @Override
    public boolean canSelectExpression() {
        return false;
    }
}
