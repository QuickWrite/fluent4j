package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.impl.container.FluentResourceBundle;

import java.util.Locale;

public final class FluentBundleBuilder {
    private FluentBundleBuilder() {}

    public static FluentBundle.Builder builder(final Locale locale) {
        return FluentResourceBundle.builder(locale);
    }
}
