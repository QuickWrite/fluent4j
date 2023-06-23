package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LeadingDotsTest {
    private static final FluentBundle<ResultBuilder> bundle;

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("dots/leading_dots.ftl");
        bundle = FluentBundleBuilder.builder(ULocale.ENGLISH).addResource(resource).build();
    }

    @Test
    public void testKey01() {
        assertEquals(
                ".Value",
                bundle.resolveMessage("key01", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey02() {
        assertEquals(
                "…Value",
                bundle.resolveMessage("key02", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey03() {
        assertEquals(
                ".Value",
                bundle.resolveMessage("key03", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey04() {
        assertEquals(
                ".Value",
                bundle.resolveMessage("key04", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey05() {
        assertEquals(
                "Value\n.Continued",
                bundle.resolveMessage("key05", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey06() {
        assertEquals(
                ".Value\n.Continued",
                bundle.resolveMessage("key06", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey07() {
        assertThrows(FluentExpectedException.class, () -> getResourceFromResource("dots/attribute_value1.ftl"));
    }

    @Test
    public void testKey08() {
        assertThrows(FluentExpectedException.class, () -> getResourceFromResource("dots/attribute_value2.ftl"));
    }

    @Test
    public void testKey09() {
        assertThrows(FluentExpectedException.class, () -> getResourceFromResource("dots/attribute_value3.ftl"));
    }

    @Test
    public void testKey10() {
        assertEquals(
                "",
                bundle.resolveMessage("key10", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey10Attribute() {
        assertEquals(
                "which is an attribute\nContinued",
                bundle.resolveMessage("key10-attribute", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey11() {
        assertEquals(
                """
                        .Value = which looks like an attribute
                        Continued""",
                bundle.resolveMessage("key11", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey12() {
        assertEquals(
                "",
                bundle.resolveMessage("key12", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey12Attribute() {
        assertEquals(
                "A",
                bundle.resolveMessage("key12-attribute", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey13() {
        assertEquals(
                "",
                bundle.resolveMessage("key13", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey13Attribute() {
        assertEquals(
                ".Value",
                bundle.resolveMessage("key13-attribute", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey14() {
        assertEquals(
                "",
                bundle.resolveMessage("key14", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey14Attribute() {
        assertEquals(
                ".Value",
                bundle.resolveMessage("key14-attribute", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey15() {
        assertEquals(
                ".Value",
                bundle.resolveMessage("key15", StringResultFactory.construct()).get().toString()
        );
    }

    /*
    This strays a little from the reference implementation. But this is just because
    the parsing is different and with that this scenario can be directly included.
    This means that even though this would be an error in the JS implementation this is
    more intuitive and with that more desirable.
     */

    @Test
    public void testKey16() {
        assertEquals(
                ".Value",
                bundle.resolveMessage("key16", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey17() {
        assertEquals(
                "Value\n.Continued",
                bundle.resolveMessage("key17", StringResultFactory.construct()).get().toString()
        );
    }
}
