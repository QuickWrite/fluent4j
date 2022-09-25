package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestCalleeExpressions {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("callee_expressions.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assert.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testFunctionCalleePlaceable() {
        Assert.assertEquals("{FUNCTION()}", GetFileHelper.getMessage(bundle, "function-callee-placeable"));
    }

    @Test
    public void testTermCalleePlaceable() {
        Assert.assertEquals("{-term}", GetFileHelper.getMessage(bundle, "term-callee-placeable"));
    }

    @Test
    public void testFunctionCalleeSelector() {
        Assert.assertEquals("Value", GetFileHelper.getMessage(bundle, "function-callee-selector"));
    }

    @Test
    public void testTermAttrCalleeSelector() {
        Assert.assertEquals("Value", GetFileHelper.getMessage(bundle, "term-attr-callee-selector"));
    }
}
