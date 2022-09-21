package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestAnyChar {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("any_char.ftl");
    }

    @Test
    public void testExceptions() {
        Assertions.assertFalse(bundle.hasExceptions());
    }

    @Test
    public void testControl0() {
        Assertions.assertEquals("abc\u0007def", GetFileHelper.getMessage(bundle, "control0"));
    }

    @Test
    public void testDelete() {
        Assertions.assertEquals("abc\u007Fdef", GetFileHelper.getMessage(bundle, "delete"));
    }

    @Test
    public void testControl1() {
        Assertions.assertEquals("abc\u0082def", GetFileHelper.getMessage(bundle, "control1"));
    }
}
