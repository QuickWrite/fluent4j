package net.quickwrite.fluent4j.functions;

public class AbstractFunction {
    private final String identifier;

    /**
     * The initializer of the function.
     * <br>
     * <hr>
     *
     * <p>
     * The identifier of the function will be changed to
     * {@code UPPERCASE} even if the identifier is completely
     * lowercase. If the function wouldn't be uppercase it cannot
     * be called at all as function identifiers are defined
     * tp be completely uppercase and so wouldn't be parsed.
     * <br>
     * So the identifier {@code hello} would result in {@code HELLO}.
     * </p>
     *
     * @param identifier The identifier on how the function should be called.
     */
    public AbstractFunction(final String identifier) {
        this.identifier = identifier.toUpperCase();
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
