package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestLeadingDots {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("leading_dots.ftl");
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals(".Value", GetFileHelper.getMessage(bundle, "key01"));
    }

    @Test
    public void testKey02() {
        Assertions.assertEquals("…Value", GetFileHelper.getMessage(bundle, "key02"));
    }

    @Test
    public void testKey03() {
        Assertions.assertEquals(".Value", GetFileHelper.getMessage(bundle, "key03"));
    }

    @Test
    public void testKey04() {
        Assertions.assertEquals(".Value", GetFileHelper.getMessage(bundle, "key04"));
    }

    @Test
    public void testKey05() {
        Assertions.assertEquals("Value\n.Continued", GetFileHelper.getMessage(bundle, "key05"));
    }

    @Test
    public void testKey06() {
        Assertions.assertEquals(".Value\n.Continued", GetFileHelper.getMessage(bundle, "key06"));
    }

    @Test
    public void testKey10() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key10"));
    }

    @Test
    public void testKey11() {
        Assertions.assertEquals(".Value = which looks like an attribute\nContinued", GetFileHelper.getMessage(bundle, "key11"));
    }

    @Test
    public void testKey12() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key12"));
    }

    @Test
    public void testKey13() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key13"));
    }

    @Test
    public void testKey14() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key12"));
    }

    @Test
    public void testKey15() {
        Assertions.assertEquals(".Value", GetFileHelper.getMessage(bundle, "key15"));
    }

    @Test
    public void testKey16() {
        Assertions.assertEquals(".Value", GetFileHelper.getMessage(bundle, "key16"));
    }

    @Test
    public void testKey17() {
        Assertions.assertEquals("Value\n.Continued", GetFileHelper.getMessage(bundle, "key17"));
    }
}
