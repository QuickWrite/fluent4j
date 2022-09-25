package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestMessages {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("messages.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "key01"));
    }

    @Test
    public void testKey02() {
        Assertions.assertEquals("Value2", GetFileHelper.getMessage(bundle, "key02"));
    }

    @Test
    public void testKey03() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key03"));
    }

    @Test
    public void testKey04() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key04"));
    }

    @Test
    public void testKey05() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key05"));
    }

    @Test
    public void testNoWhitespace() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "no-whitespace"));
    }

    @Test
    public void testExtraWhitespace() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "extra-whitespace"));
    }

    @Test
    public void testKey06() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key06"));
    }

    @Test
    public void testKey09() {
        Assertions.assertEquals("Value 09", GetFileHelper.getMessage(bundle, "KEY09"));
    }

    @Test
    public void testKey10() {
        Assertions.assertEquals("Value 10", GetFileHelper.getMessage(bundle, "key-10"));
    }

    @Test
    public void testKey11() {
        Assertions.assertEquals("Value 11", GetFileHelper.getMessage(bundle, "key_11"));
    }

    @Test
    public void testKey12() {
        Assertions.assertEquals("Value 12", GetFileHelper.getMessage(bundle, "key-12-"));
    }

    @Test
    public void testKey13() {
        Assertions.assertEquals("Value 13", GetFileHelper.getMessage(bundle, "key_13_"));
    }
}
