package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.util.StringSlice;

/**
 * The number literal stores numbers. These numbers
 * are stored in two different containers depending
 * on their type.
 *
 * <p>
 * Numbers can be integers or rational numbers
 * </p>
 */
public abstract class NumberLiteral implements FluentPlaceable, FluentSelectable {
    private NumberLiteral() {
    }

    public static net.quickwrite.fluent4j.ast.placeable.NumberLiteral getNumberLiteral(StringSlice slice) {
        try {
            return new IntegerLiteral(Integer.parseInt(slice.toString()));
        } catch (NumberFormatException ignored) {
        }

        try {
            return new DoubleLiteral(Double.parseDouble(slice.toString()));
        } catch (NumberFormatException ignored) {
        }

        throw new FluentParseException("Number", slice.toString(), slice.getPosition());
    }

    public abstract Number getNumber();

    @Override
    public StringSlice getContent() {
        return null;
    }

    @Override
    public String toString() {
        return "FluentNumberLiteral: {\n" +
                "\t\t\tvalue: \"" + this.getNumber() + "\"\n" +
                "\t\t}";
    }

    private static class IntegerLiteral extends net.quickwrite.fluent4j.ast.placeable.NumberLiteral {
        private final int value;

        public IntegerLiteral(int value) {
            this.value = value;
        }

        @Override
        public Number getNumber() {
            return value;
        }
    }

    private static class DoubleLiteral extends net.quickwrite.fluent4j.ast.placeable.NumberLiteral {
        private final double value;

        public DoubleLiteral(double value) {
            this.value = value;
        }

        @Override
        public Number getNumber() {
            return value;
        }
    }
}
