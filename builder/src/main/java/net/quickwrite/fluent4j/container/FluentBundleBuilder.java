package net.quickwrite.fluent4j.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.impl.container.FluentResourceBundle;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Locale;

public final class FluentBundleBuilder {
    private FluentBundleBuilder() {}

    public static <B extends ResultBuilder> FluentBundle.Builder<B> builder(final Locale locale) {
        return builder(ULocale.forLocale(locale));
    }

    public static <B extends ResultBuilder> FluentBundle.Builder<B> builder(final ULocale locale) {
        return FluentResourceBundle.builder(locale);
    }
}
