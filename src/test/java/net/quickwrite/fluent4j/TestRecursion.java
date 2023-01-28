package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.exception.RecursionDepthReachedException;
import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestRecursion {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("recursion.ftl");
    }

    @Test
    public void testRecursiveKey01() {
        Assertions.assertThrows(RecursionDepthReachedException.class, () -> {
            GetFileHelper.getMessage(bundle, "recursive-key01");
        });
    }

    @Test
    public void testRecursiveTerm01() {
        Assertions.assertThrows(RecursionDepthReachedException.class, () -> {
            GetFileHelper.getMessage(bundle, "recursive-term01");
        });
    }
}
