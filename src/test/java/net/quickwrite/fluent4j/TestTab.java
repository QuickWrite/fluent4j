package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestTab {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("tab.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals("\tValue 01", GetFileHelper.getMessage(bundle, "key01"));
    }

    @Test
    public void testKey03() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key03"));
    }

    @Test
    public void testKey04() {
        Assertions.assertEquals("This line is indented by 4 spaces,", GetFileHelper.getMessage(bundle, "key04"));
    }
}
