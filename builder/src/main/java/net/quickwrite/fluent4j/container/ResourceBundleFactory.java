package net.quickwrite.fluent4j.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.impl.container.FluentResourceBundle;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class ResourceBundleFactory implements FluentBundleFactory {
    @Override
    public <B extends ResultBuilder> FluentBundle<B> create(ULocale locale) {
        return new FluentResourceBundle<>(locale);
    }
}
