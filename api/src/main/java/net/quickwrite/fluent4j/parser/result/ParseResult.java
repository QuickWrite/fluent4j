package net.quickwrite.fluent4j.parser.result;

/**
 * The result of a parsing step.
 *
 * <p>
 *     The result of the parsing step can be divided into
 *     three different results:
 *     <ol type="1">
 *         <li>
 *             {@code Success} which indicates that there is a
 *              valid result of this parsing step.
 *
 *              <br />
 *
 *              The result of this parsing step also contains
 *              the resulting object that was being generated
 *              by this.
 *         </li>
 *         <li>
 *             {@code Skip} which indicates that the parsing was
 *              successful but didn't have any result.
 *
 *              <br />
 *
 *              The result in this case is {@code null}.
 *         </li>
 *         <li>
 *             {@code Failure} which indicates that the parsing
 *             step didn't go as planned and needs to be either
 *             reevaluated to ditched completely.
 *
 *             <br />
 *
 *             The result in this case is {@code null}.
 *         </li>
 *     </ol>
 * </p>
 *
 * <p>
 *     This is similar to the {@link java.util.Optional} but it has the
 *     key difference that this object has three different states instead
 *     of just two.
 * </p>
 * @param <T> The value type that the result should have
 *           if it was successful
 */
public interface ParseResult<T> {
    /**
     * Create a successful parsing result
     *
     * @param value The value that should be returned
     * @return A {@link ParseResult} that contains the value
     *         and indicates that this was successful
     * @param <V> The type of the resulting value
     */
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

    /**
     * Returns a simple failure parse result
     *
     * <p>
     *     This can indicate that something went wrong
     *     while parsing or the parser is not the correct
     *     parser for this section
     * </p>
     *
     * @return The parse result that indicates a failure
     * @param <V> The value type that the result should have
     *           if it was successful
     */
    @SuppressWarnings("unchecked")
    static <V> ParseResult<V> failure() {
        return (ParseResult<V>) ParseResultType.FAILURE_IMPL;
    }

    /**
     * Returns a simple skip parse result
     *
     * @return The parse result that indicates a skip
     * @param <V> The value type that the result should have
     *           if it was successful
     */
    @SuppressWarnings("unchecked")
    static <V> ParseResult<V> skip() {
        return (ParseResult<V>) ParseResultType.SKIP_IMPL;
    }

    /**
     * Returns the type that the ParseResult contains
     *
     * @return The type of the ParseResult
     */
    ParseResultType getType();

    /**
     * Returns the value of the ParseResult if it is
     * successful.
     *
     * <p>
     *     If it wasn't a successful ParseResult
     *     it is going to return a {@code null} value.
     * </p>
     *
     * @return The value of the ParseResult
     */
    T getValue();

    /**
     * The result types of the parsing step can be indicated by these
     * different types of the result:
     * <ol type="1">
     *     <li>
     *         {@code Success} which indicates that there is a
     *          valid result of this parsing step.
     *
     *          <br />
     *
     *          The result of this parsing step also contains
     *          the resulting object that was being generated
     *          by this.
     *     </li>
     *     <li>
     *         {@code Skip} which indicates that the parsing was
     *          successful but didn't have any result.
     *
     *          <br />
     *
     *          The result in this case is {@code null}.
     *     </li>
     *     <li>
     *         {@code Failure} which indicates that the parsing
     *         step didn't go as planned and needs to be either
     *         reevaluated to ditched completely.
     *
     *         <br />
     *
     *         The result in this case is {@code null}.
     *     </li>
     * </ol>
     */
    enum ParseResultType {
        /**
         * This indicates that the parsing step was
         * successful and a result was being created
         */
        SUCCESS,

        /**
         * This indicates that the parsing step was
         * successful but no result was being created
         * and this part should just be skipped
         */
        SKIP,

        /**
         * This indicates that the parsing step wasn't
         * successful and this part either needs to be
         * reevaluated or the parsing should be discontinued
         * entirely.
         */
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
