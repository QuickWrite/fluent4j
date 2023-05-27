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

public class SparseEntriesTest {
    private static final FluentBundle<ResultBuilder> bundle;

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("multiline/sparse_entries.ftl");
        bundle = ResourceBundleFactory.forLocale(ULocale.ENGLISH);
        bundle.addResource(resource);
    }

    @Test
    public void testKey01() {
        assertEquals(
                "Value",
                bundle.resolveMessage("key01", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey02() {
        assertEquals(
                "",
                bundle.resolveMessage("key02", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey02Attribute() {
        assertEquals(
                "Attribute",
                bundle.resolveMessage("key02-attribute", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey03() {
        assertEquals(
                """
                        Value
                        Continued
                        
                        
                        Over multiple
                        Lines""",
                bundle.resolveMessage("key03", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey03Attribute() {
        assertEquals(
                "Attribute",
                bundle.resolveMessage("key03-attribute", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey05() {
        assertEquals(
                "Value",
                bundle.resolveMessage("key05", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey06() {
        assertEquals(
                "One",
                bundle.resolveMessage("key06", StringResultFactory.construct()).get().toString()
        );
    }
}
