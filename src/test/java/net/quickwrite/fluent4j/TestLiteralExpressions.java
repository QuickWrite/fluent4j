package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestLiteralExpressions {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("literal_expressions.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertFalse(bundle.hasExceptions());
    }

    @Test
    public void testStringExpression() {
        Assertions.assertEquals("abc", GetFileHelper.getMessage(bundle, "string-expression"));
    }

    @Test
    public void testNumberExpression() {
        Assertions.assertEquals("123", GetFileHelper.getMessage(bundle, "number-expression"));
    }

    @Test
    public void testNegativeNumberExpression() {
        Assertions.assertEquals("-42", GetFileHelper.getMessage(bundle, "negative-number-expression"));
    }

    @Test
    public void testFloatExpression() {
        Assertions.assertEquals("3.141", GetFileHelper.getMessage(bundle, "float-expression"));
    }

    @Test
    public void testNegativeFloatExpression() {
        Assertions.assertEquals("-6.282", GetFileHelper.getMessage(bundle, "negative-float-expression"));
    }
}
