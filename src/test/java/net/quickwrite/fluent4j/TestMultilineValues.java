package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestMultilineValues {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("multiline_values.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertFalse(bundle.hasExceptions());
    }

    @Test
    public void testKey01() {
        Assertions.assertEquals("""
                A multiline value
                continued on the next line
                
                and also down here.""",
                GetFileHelper.getMessage(bundle, "key01"));
    }

    @Test
    public void testKey02() {
        Assertions.assertEquals("""
                A multiline value starting
                on a new line.""",
                GetFileHelper.getMessage(bundle, "key02"));
    }

    @Test
    public void testKey03() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key03"));
    }

    @Test
    public void testKey04() {
        Assertions.assertEquals("", GetFileHelper.getMessage(bundle, "key04"));
    }

    @Test
    public void testKey05() {
        Assertions.assertEquals("""
                A multiline value with non-standard
                    
                    indentation.""",
                GetFileHelper.getMessage(bundle, "key05"));
    }

    @Test
    public void testKey06() {
        Assertions.assertEquals("""
                A multiline value with placeables
                at the beginning and the end
                of lines.""",
                GetFileHelper.getMessage(bundle, "key06"));
    }

    @Test
    public void testKey07() {
        Assertions.assertEquals("A multiline value starting and ending with a placeable", GetFileHelper.getMessage(bundle, "key07"));
    }

    @Test
    public void testKey08() {
        Assertions.assertEquals("Leading and trailing whitespace.", GetFileHelper.getMessage(bundle, "key08"));
    }

    @Test
    public void testKey09() {
        Assertions.assertEquals("""
                zero
                   three
                  two
                 one
                zero""",
                GetFileHelper.getMessage(bundle, "key09"));
    }

    @Test
    public void testKey10() {
        Assertions.assertEquals("""
                  two
                zero
                    four""",
                GetFileHelper.getMessage(bundle, "key10"));
    }

    @Test
    public void testKey11() {
        Assertions.assertEquals("""
                  two
                zero""",
                GetFileHelper.getMessage(bundle, "key11"));
    }
}
