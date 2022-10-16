package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.builder.FluentArgsBuilder;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestScopes {
    private FluentBundle bundle;
    private FluentArgs args;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("scopes.ftl");
        this.args = new FluentArgsBuilder().set("var1", "Hello").set("var2", "World").build();
    }

    @Test
    public void testTopScope() {
        Assertions.assertEquals("Hello World", bundle.getMessage("top-scope", args).orElseThrow());
    }

    @Test
    public void testMessageScope1() {
        Assertions.assertEquals("Hello World", bundle.getMessage("message-scope1", args).orElseThrow());
    }

    @Test
    public void testMessageScope2() {
        Assertions.assertEquals("Hello World", bundle.getMessage("message-scope2", args).orElseThrow());
    }

    @Test
    public void testTermScope1() {
        Assertions.assertEquals("{$var1} {$var2}", bundle.getMessage("term-scope1", args).orElseThrow());
    }

    @Test
    public void testTermScope2() {
        Assertions.assertEquals("Hello World", bundle.getMessage("term-scope2", args).orElseThrow());
    }
}
