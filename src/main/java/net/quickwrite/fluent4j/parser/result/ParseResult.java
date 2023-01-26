package net.quickwrite.fluent4j.parser.result;

public interface ParseResult<T> {
    ParseResultType getType();

    T getValue();

    static <V> ParseResult<V> success(final V value) {
        return new ParseResult<>() {
            @Override
            public ParseResultType getType() {
                return ParseResultType.SUCESS;
            }

            @Override
            public V getValue() {
                return value;
            }
        };
    }

    static ParseResult<?> failure() {
        return new ParseResult<>() {
            @Override
            public ParseResultType getType() {
                return ParseResultType.FAILURE;
            }

            @Override
            public Object getValue() {
                return null;
            }
        };
    }

    static ParseResult<?> skip() {
        return new ParseResult<>() {
            @Override
            public ParseResultType getType() {
                return ParseResultType.SKIP;
            }

            @Override
            public Object getValue() {
                return null;
            }
        };
    }

    enum ParseResultType {
        SUCESS,
        SKIP,
        FAILURE
    }
}
