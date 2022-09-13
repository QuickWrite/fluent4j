package net.quickwrite.fluent4j;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.builder.FluentBundleBuilder;
import net.quickwrite.fluent4j.util.bundle.FluentBundle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class GetFileHelper {
    public static File getFile(final String filename) {
        ClassLoader classLoader = GetFileHelper.class.getClassLoader();
        File dir = new File(Objects.requireNonNull(classLoader.getResource("input")).getFile());

        return new File(dir.getAbsolutePath() + "/" + filename);
    }

    public static FluentBundle getFluentBundle(final String filename, ULocale locale) throws IOException {
        return new FluentBundleBuilder(locale, Files.readString(getFile(filename).toPath())).build();
    }

    public static FluentBundle getFluentBundle(final String filename) throws IOException {
        return getFluentBundle(filename, ULocale.ENGLISH);
    }
}
