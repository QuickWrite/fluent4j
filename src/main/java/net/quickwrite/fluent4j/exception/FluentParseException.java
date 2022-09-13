package net.quickwrite.fluent4j.exception;

public class FluentParseException extends RuntimeException {
    public FluentParseException(String expected, String got, int index) {
        super(
                "Expected " +
                        replace(expected) +
                        " but got " +
                        replace(got) +
                        " at " +
                        index
        );
    }

    private static String replace(String input) {
        if (input.equals(" ")) {
            return "\" \"";
        }

        return input.replace("\n", "\\n");
    }

    public FluentParseException(char expected, char got, int index) {
        this(Character.toString(expected), Character.toString(got), index);
    }

    public FluentParseException(String expected, char got, int index) {
        this(expected, Character.toString(got), index);
    }

    public FluentParseException(char expected, String got, int index) {
        this(Character.toString(expected), got, index);
    }

    public FluentParseException(String reason) {
        super(reason);
    }

    public FluentParseException(String reason, Exception exception) {
        super(reason, exception);
    }
}
