package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.container.ResourceBundleFactory;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecursionTest {
    private static final FluentBundle<ResultBuilder> bundle;

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("recursion/recursion.ftl");
        bundle = ResourceBundleFactory.forLocale(ULocale.ENGLISH);
        bundle.addResource(resource);
    }

    @Test
    public void testRecursiveMessage1() {
        // TODO: Better exception
        assertThrows(
                RuntimeException.class,
                () -> bundle.resolveMessage("recursive-message1", StringResultFactory.construct())
        );
    }

    @Test
    public void testRecursiveTerm1() {
        // TODO: Better exception
        assertThrows(
                RuntimeException.class,
                () -> bundle.resolveMessage("recursive-term1", StringResultFactory.construct())
        );
    }

    @Test
    public void testRecursiveSelector() {
        // TODO: Better exception
        assertThrows(
                RuntimeException.class,
                () -> bundle.resolveMessage("recursive-selector", StringResultFactory.construct())
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
