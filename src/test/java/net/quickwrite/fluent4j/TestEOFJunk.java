package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestEOFJunk {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("eof_junk.ftl");
    }
    
    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }
}
