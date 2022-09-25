package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestVariantKeys {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("variant_keys.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testSimpleIdentifier() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "simple-identifier"));
    }

    @Test
    public void testIdentifierSurroundedByWhitespace() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "identifier-surrounded-by-whitespace"));
    }

    @Test
    public void testIntNumberIdentifier() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "int-number"));
    }

    @Test
    public void testFloatNumberIdentifier() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "float-number"));
    }
}
