package net.quickwrite.fluent4j.test;

import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnyCharTest {
    private static final FluentBundle bundle;

    static {
        final FluentResource resource = getResourceFromResource("utf8/any_char.ftl");
        bundle = FluentBundleBuilder.builder(Locale.ENGLISH).addResource(resource).build();
    }

    @Test
    public void testControl0() {
        assertEquals(
                "abc\u0007def",
                bundle.resolveMessage("control0", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testDelete() {
        assertEquals(
                "abc\u007Fdef",
                bundle.resolveMessage("delete", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testControl1() {
        assertEquals(
                "abc\u0082def",
                bundle.resolveMessage("control1", StringResultFactory.construct()).get().toString()
        );
    }
}
