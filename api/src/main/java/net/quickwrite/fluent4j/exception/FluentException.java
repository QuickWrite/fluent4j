package net.quickwrite.fluent4j.exception;

/**
 * The default exception class that is being thrown by
 * fluent itself so that they can be differentiated.
 */
public abstract class FluentException extends Exception {
    public FluentException() {
        super();
    }

    public FluentException(final String message) {
        super(message);
    }

    public FluentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FluentException(final Throwable cause) {
        super(cause);
    }

    public FluentException(final String message, final Throwable cause,
                           final boolean enableSuppression,
                           final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
