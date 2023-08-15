package net.quickwrite.fluent4j.test;

import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecursionTest {
    private static final FluentBundle bundle;

    static {
        final FluentResource resource = getResourceFromResource("recursion/recursion.ftl");
        bundle = FluentBundleBuilder.builder(Locale.ENGLISH).addResource(resource).build();
    }

    @Test
    public void testRecursiveMessage1() {
        assertEquals(
                "{???}",
                bundle.resolveMessage("recursive-message1", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testRecursiveTerm1() {
        assertEquals(
                "{???}",
                bundle.resolveMessage("recursive-term1", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testRecursiveTerm2() {
        assertEquals(
                "This is a recursive {???}.",
                bundle.resolveMessage("recursive-term2", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testRecursiveSelector() {
        assertEquals(
                "Test",
                bundle.resolveMessage("recursive-selector", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testNonRecursion() {
        assertEquals(
                "Value",
                bundle.resolveMessage(
                        "non-recursion",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testDoubleNonRecursion() {
        assertEquals(
                "Value Value",
                bundle.resolveMessage(
                        "double-non-recursion",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }
}
