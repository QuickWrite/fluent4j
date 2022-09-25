package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestCRLF {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("crlf.ftl");
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals("Value 01", GetFileHelper.getMessage(bundle, "key01"));
    }

    @Test
    public void testKey02() {
        Assertions.assertEquals("Value 02\nContinued", GetFileHelper.getMessage(bundle, "key02"));
    }
}
