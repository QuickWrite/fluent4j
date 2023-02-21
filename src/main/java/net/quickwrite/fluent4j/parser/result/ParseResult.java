package net.quickwrite.fluent4j.parser.result;

public interface ParseResult<T> {
    static <V> ParseResult<V> success(final V value) {
        return new ParseResult<>() {
            @Override
            public ParseResultType getType() {
                return ParseResultType.SUCCESS;
            }

            @Override
            public V getValue() {
                return value;
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <V> ParseResult<V> failure() {
        return (ParseResult<V>) ParseResultType.FAILURE_IMPL;
    }

    @SuppressWarnings("unchecked")
    static <V> ParseResult<V> skip() {
        return (ParseResult<V>) ParseResultType.SKIP_IMPL;
    }

    ParseResultType getType();

    T getValue();

    enum ParseResultType {
        SUCCESS,
        SKIP,
        FAILURE;

        static final ParseResult<?> FAILURE_IMPL = new ParseResult<>() {
            @Override
            public ParseResultType getType() {
                return FAILURE;
            }

            @Override
            public Object getValue() {
                return null;
            }
        };

        static final ParseResult<?> SKIP_IMPL = new ParseResult<>() {
            @Override
            public ParseResultType getType() {
                return SKIP;
            }

            @Override
            public Object getValue() {
                return null;
            }
        };
    }
}
