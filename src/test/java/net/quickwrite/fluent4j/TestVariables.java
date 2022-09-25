package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.builder.FluentArgsBuilder;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestVariables {
    private FluentBundle bundle;
    private FluentArgs arguments;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("variables.ftl");
        this.arguments = new FluentArgsBuilder().set("var", "Value").build();
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals(
                "Value",
                bundle.getMessage("key01", arguments).orElseThrow()
        );
    }

    @Test
    public void testKey02() {
        Assertions.assertEquals(
                "Value",
                bundle.getMessage("key02", arguments).orElseThrow()
        );
    }

    @Test
    public void testKey03() {
        Assertions.assertEquals(
                "Value",
                bundle.getMessage("key03", arguments).orElseThrow()
        );
    }

    @Test
    public void testKey04() {
        Assertions.assertEquals(
                "Value",
                bundle.getMessage("key04", arguments).orElseThrow()
        );
    }
}
