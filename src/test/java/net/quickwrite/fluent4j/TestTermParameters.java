package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestTermParameters {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("term_parameters.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertFalse(bundle.hasExceptions());
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "key01"));
    }

    @Test
    public void testKey02() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "key02"));
    }

    @Test
    public void testKey03() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "key03"));
    }

    @Test
    public void testKey04() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "key04"));
    }
}
