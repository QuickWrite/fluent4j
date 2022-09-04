package net.quickwrite.fluent4j.builder;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.parser.FluentParser;

import java.io.File;

public class FluentBundleBuilder extends AbstractBuilder<FluentBundle> {
    public FluentBundleBuilder(final ULocale locale, final File file) {
        this(locale, file.toString());
    }

    public FluentBundleBuilder(final ULocale locale, final String fileContent) {
        super(new FluentBundle(locale, FluentParser.parse(fileContent)));
    }

    public FluentBundleBuilder addResource(final File file) {
        return addResource(file.toString());
    }

    public FluentBundleBuilder addResource(final String fileContent) {
        this.element.addResource(FluentParser.parse(fileContent));

        return this;
    }

    public FluentBundleBuilder addFunction(final AbstractFunction function) {
        this.element.addFunction(function);

        return this;
    }
}
