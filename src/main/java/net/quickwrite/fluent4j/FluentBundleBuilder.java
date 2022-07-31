package net.quickwrite.fluent4j;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.parser.FluentParser;

import java.io.File;

public class FluentBundleBuilder {
    private final FluentBundle fluentBundle;

    public FluentBundleBuilder(final ULocale locale, final File file) {
        this(locale, file.toString());
    }

    public FluentBundleBuilder(final ULocale locale, final String fileContent) {
        final FluentParser fluentParser = new FluentParser(fileContent);

       this.fluentBundle = new FluentBundle(locale, fluentParser.parse());
    }

    public FluentBundleBuilder addResource(final File file) {
        return addResource(file.toString());
    }

    public FluentBundleBuilder addResource(final String fileContent) {
        final FluentParser fluentParser = new FluentParser(fileContent);
        this.fluentBundle.addResource(fluentParser.parse());

        return this;
    }

    public FluentBundleBuilder addFunction(final AbstractFunction function) {
        this.fluentBundle.addFunction(function);

        return this;
    }

    public FluentBundle build() {
        return this.fluentBundle;
    }
}
