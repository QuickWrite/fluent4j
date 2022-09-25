package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestMemberExpressions {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("member_expressions.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertFalse(bundle.hasExceptions());
    }

    @Test
    public void testMessageAttributeExpressionPlaceable() {
        Assertions.assertEquals("{msg.attr}", GetFileHelper.getMessage(bundle, "message-attribute-expression-placeable"));
    }

    @Test
    public void testTermAttributeExpressionPlaceable() {
        Assertions.assertEquals("{-term.attr}", GetFileHelper.getMessage(bundle, "term-attribute-expression-placeable"));
    }

    @Test
    public void testTermAttributeExpressionSelector() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "term-attribute-expression-selector"));
    }

    @Test
    public void testMessageAttributeExpressionSelector() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "message-attribute-expression-selector"));
    }
}
