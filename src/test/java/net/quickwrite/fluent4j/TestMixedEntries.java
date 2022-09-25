package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestMixedEntries {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("mixed_entries.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key01"));
    }

    @Test
    public void testNonASCIICharacter1() {
        Assertions.assertEquals("Custom Identifier", GetFileHelper.getMessage(bundle, "ą"));
    }

    @Test
    public void testNonASCIICharacter2() {
        Assertions.assertEquals("Another one", GetFileHelper.getMessage(bundle, "ć"));
    }

    @Test
    public void testKey02() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "key02"));
    }

    @Test
    public void testKey03() {
        Assertions.assertEquals("Value 03", GetFileHelper.getMessage(bundle, "key03"));
    }

    @Test
    public void testKey04() {
        Assertions.assertEquals("Value 04", GetFileHelper.getMessage(bundle, "key04"));
    }
}
