package net.quickwrite.fluent4j.exception;

public class FluentParseException extends RuntimeException {
    public FluentParseException(String expected, String got, int index) {
        super("Expected " + expected + " but got " + got + " at " + index);
    }
}
