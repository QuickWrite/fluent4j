package net.quickwrite.fluent4j.impl.util;

import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.function.Consumer;

public final class ErrorUtil {
    private ErrorUtil() {}

    public static <B extends ResultBuilder> void writePlaceable(final B builder, final CharSequence string) {
        builder.append('{').append(string).append('}');
    }

    public static <B extends ResultBuilder> void writePlaceable(final B builder, final Consumer<B> consumer) {
        builder.append('{');
        consumer.accept(builder);
        builder.append('}');
    }

    public static FluentPatternException getPlaceablePatternException(final CharSequence string) {
        return FluentPatternException.getDefault((writer -> ErrorUtil.writePlaceable(writer, string)));
    }

    public static FluentPatternException getPlaceablePatternException(final Consumer<ResultBuilder> consumer) {
        return FluentPatternException.getDefault((writer -> ErrorUtil.writePlaceable(writer, consumer)));
    }
}
