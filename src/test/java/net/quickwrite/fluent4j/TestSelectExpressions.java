package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestSelectExpressions {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("select_expressions.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testNewMessages() {
        Assertions.assertEquals("Other", GetFileHelper.getMessage(bundle, "new-messages"));
    }

    @Test
    public void testValidSelectorTermAttribute() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "valid-selector-term-attribute"));
    }

    @Test
    public void testEmptyVariant() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "empty-variant"));
    }

    @Test
    public void testReducedWhitespace() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "reduced-whitespace"));
    }

    @Test
    public void testNestedSelect() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "nested-select"));
    }
}
