package net.quickwrite.fluent4j.container.exception;

public class FluentSelectException extends Exception {
    public FluentSelectException() {
        super();
    }

    public FluentSelectException(final String message) {
        super(message);
    }

    public FluentSelectException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
