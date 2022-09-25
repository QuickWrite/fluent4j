package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.builder.FluentArgsBuilder;
import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestReferenceExpressions {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("reference_expressions.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testMessageReferencePlaceable() {
        Assertions.assertEquals("{msg}", GetFileHelper.getMessage(bundle, "message-reference-placeable"));
    }

    @Test
    public void testTermReferencePlaceable() {
        Assertions.assertEquals("{-term}", GetFileHelper.getMessage(bundle, "term-reference-placeable"));
    }

    @Test
    public void testVariableReferencePlaceable() {
        Assertions.assertEquals("{$var}", GetFileHelper.getMessage(bundle, "variable-reference-placeable"));
    }

    @Test
    public void testVariableReferenceSelector() {
        Assertions.assertEquals("Value", GetFileHelper.getMessage(bundle, "variable-reference-selector"));
    }

    @Test
    public void testValidMessageReferencePlaceable() {
        Assertions.assertEquals("Valid Message", GetFileHelper.getMessage(bundle, "valid-message-reference-placeable"));
    }

    @Test
    public void testValidTermReferencePlaceable() {
        Assertions.assertEquals("Valid Term", GetFileHelper.getMessage(bundle, "valid-term-reference-placeable"));
    }

    @Test
    public void testValidVariableReferencePlaceable() {
        Assertions.assertEquals("Value",
                bundle.getMessage("valid-variable-reference-placeable",
                    new FluentArgsBuilder().set("var", "Value").build()
                ).orElseThrow()
        );
    }
}
