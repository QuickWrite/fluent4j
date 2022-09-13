package net.quickwrite.fluent4j.exception;

public class UnknownElementException extends RuntimeException {
    public UnknownElementException(final String reason) {
        super(reason);
    }
}
