package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestCR {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("cr.ftl");
    }

    @Test
    public void testErr01() {
        Assertions.assertEquals("Value 01", GetFileHelper.getMessage(bundle, "err01"));
    }

    @Test
    public void testErr02() {
        Assertions.assertEquals("Value 02", GetFileHelper.getMessage(bundle, "err02"));
    }

    @Test
    public void testErr03() {
        Assertions.assertEquals("Value 03\nContinued", GetFileHelper.getMessage(bundle, "err03"));
    }
}
