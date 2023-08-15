package net.quickwrite.fluent4j.test;

import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhitespaceTest {
    @Test
    public void testZeroLength() {
        final FluentResource resource = getResourceFromResource("whitespace/zero_length.ftl");

        assertEquals(0, resource.entries().length);
    }

    @Test
    public void testWhitespace() {
        final FluentResource resource = getResourceFromResource("whitespace/whitespace.ftl");

        assertEquals(0, resource.entries().length);
    }

    @Test
    public void testWhitespaceInValue() {
        final FluentResource resource = getResourceFromResource("whitespace/whitespace_in_value.ftl");

        final FluentBundle bundle = FluentBundleBuilder.builder(Locale.ENGLISH).addResource(resource).build();

        assertEquals("""
                        first line
                                        
                                        
                                        
                                        
                                        
                                        
                        last line""",
                bundle.resolveMessage("key", StringResultFactory.construct())
                        .get()
                        .toString()
        );
    }
}
