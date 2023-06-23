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

public class MultilineValuesTest {
    private static final FluentBundle<ResultBuilder> bundle;

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("multiline/multiline_values.ftl");
        bundle = ResourceBundleFactory.forLocale(ULocale.ENGLISH);
        bundle.addResource(resource);
    }

    @Test
    public void testKey01() {
        assertEquals(
                """
                        A multiline value
                        continued on the next line
                                                
                        and also down here.""",
                bundle.resolveMessage("key01",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey02() {
        assertEquals(
                """
                        A multiline value
                        starting on a new line.""",
                bundle.resolveMessage("key02",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey03() {
        assertEquals(
                "",
                bundle.resolveMessage("key03",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey03Attribute() {
        assertEquals(
                """
                        A multiline attribute value
                        continued on the next line
                                                
                        and also down here.""",
                bundle.resolveMessage("key03-attribute",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey04() {
        assertEquals(
                "",
                bundle.resolveMessage("key04",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey04Attribute() {
        assertEquals(
                """
                        A multiline attribute value
                        starting on a new line.""",
                bundle.resolveMessage("key04-attribute",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey05() {
        assertEquals(
                """
                        A multiline value with non-standard
                                                
                            indentation.""",
                bundle.resolveMessage("key05",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey06() {
        assertEquals(
                """
                        A multiline value with placeables
                        at the beginning and the end
                        of lines.""",
                bundle.resolveMessage("key06",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey07() {
        assertEquals(
                """
                        A multiline value starting and ending with a placeable.""",
                bundle.resolveMessage("key07",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey08() {
        assertEquals(
                """
                        Leading and trailing whitespace.""",
                bundle.resolveMessage("key08",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey09() {
        assertEquals(
                """
                        zero
                           three
                          two
                         one
                        zero""",
                bundle.resolveMessage("key09",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey10() {
        assertEquals(
                """
                          two
                        zero
                            four""",
                bundle.resolveMessage("key10",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey11() {
        assertEquals(
                """
                          two
                        zero""",
                bundle.resolveMessage("key11",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }
}
