package net.quickwrite.fluent4j.test;

import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.ResultBuilder;
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
}
