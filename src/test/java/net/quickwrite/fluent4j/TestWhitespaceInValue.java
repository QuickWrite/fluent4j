package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestWhitespaceInValue {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("whitespace_in_value.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertFalse(bundle.hasExceptions());
    }

    @Test
    public void testKey() {
        Assertions.assertEquals("""
                first line
                
                
                
                
                
                
                last line""",
                GetFileHelper.getMessage(bundle, "key")
        );
    }
}
