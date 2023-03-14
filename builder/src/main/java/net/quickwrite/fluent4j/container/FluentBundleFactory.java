package net.quickwrite.fluent4j.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Locale;

public interface FluentBundleFactory {
    <B extends ResultBuilder> FluentBundle<B> create(final ULocale locale);

    default <B extends ResultBuilder> FluentBundle<B> create(final Locale locale) {
        return create(ULocale.forLocale(locale));
    }
}
