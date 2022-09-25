package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestComments {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("comments.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertFalse(bundle.hasExceptions());
    }

    @Test
    public void testMessage() {
        Assertions.assertEquals("Foo", GetFileHelper.getMessage(bundle, "foo"));
    }
}
