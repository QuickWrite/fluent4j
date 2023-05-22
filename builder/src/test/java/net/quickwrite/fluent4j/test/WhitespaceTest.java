package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.container.ResourceBundleFactory;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.ResultBuilderFactory;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhitespaceTest {
    @Test
    public void testZeroLength() {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("whitespace/zero_length.ftl");

        assertEquals(0, resource.entries().size());
    }

    @Test
    public void testWhitespace() {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("whitespace/whitespace.ftl");

        assertEquals(0, resource.entries().size());
    }

    @Test
    public void testWhitespaceInValue() {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("whitespace/whitespace_in_value.ftl");

        final FluentBundle<ResultBuilder> bundle = ResourceBundleFactory.forLocale(ULocale.ENGLISH);
        bundle.addResource(resource);

        assertEquals("""
                first line
                
                
                
                
                
                
                last line""",
                bundle.resolveMessage("key", StringResultFactory.construct())
                        .get()
                        .toString()
        );
    }
}
