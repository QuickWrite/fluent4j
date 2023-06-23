package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberFunctionTest {
    private static final FluentBundle<ResultBuilder> bundleEnglish;
    private static final FluentBundle<ResultBuilder> bundleGerman;

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("functions/number_function.ftl");

        bundleEnglish = FluentBundleBuilder.builder(ULocale.ENGLISH).addDefaultFunctions().addResource(resource).build();

        bundleGerman = FluentBundleBuilder.builder(ULocale.GERMAN).addDefaultFunctions().addResource(resource).build();
    }

    @Test
    public void testKey01English() {
        assertEquals(
                "1.0",
                bundleEnglish.resolveMessage("key01", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey01German() {
        assertEquals(
                "1,0",
                bundleGerman.resolveMessage("key01", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey02English() {
        assertEquals(
                "1.00000",
                bundleEnglish.resolveMessage("key02", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey02German() {
        assertEquals(
                "1,00000",
                bundleGerman.resolveMessage("key02", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey03English() {
        assertEquals(
                "1.1",
                bundleEnglish.resolveMessage("key03", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey03German() {
        assertEquals(
                "1,1",
                bundleGerman.resolveMessage("key03", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey04English() {
        assertEquals(
                "1.11111",
                bundleEnglish.resolveMessage("key04", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey04German() {
        assertEquals(
                "1,11111",
                bundleGerman.resolveMessage("key04", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey05English() {
        assertEquals(
                "00001.5",
                bundleEnglish.resolveMessage("key05", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey05German() {
        assertEquals(
                "00001,5",
                bundleGerman.resolveMessage("key05", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey06English() {
        assertEquals(
                "00001.50000",
                bundleEnglish.resolveMessage("key06", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey06German() {
        assertEquals(
                "00001,50000",
                bundleGerman.resolveMessage("key06", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey07English() {
        assertEquals(
                "00001.11",
                bundleEnglish.resolveMessage("key07", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testKey07German() {
        assertEquals(
                "00001,11",
                bundleGerman.resolveMessage("key07", StringResultFactory.construct()).get().toString()
        );
    }
}
