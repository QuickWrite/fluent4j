package net.quickwrite.fluent4j.test;

import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DuplicateEntryTest {
    private static final FluentResource resource01;
    private static final FluentResource resource02;

    static {
        resource01 = getResourceFromResource("duplicate/duplicate_entries01.ftl");
        resource02 = getResourceFromResource("duplicate/duplicate_entries02.ftl");
    }

    @Test
    public void testDuplicateEntryException01() {
        assertThrows(
                FluentBundle.DuplicateEntryException.class,
                () -> FluentBundleBuilder.builder(Locale.ENGLISH)
                        .addResource(resource01)
                        .addResourceNoDup(resource01)
        );
    }

    @Test
    public void testDuplicateEntryException02() {
        assertThrows(
                FluentBundle.DuplicateEntryException.class,
                () -> FluentBundleBuilder.builder(Locale.ENGLISH)
                        .addResource(resource02)
                        .addResourceNoDup(resource02)
        );
    }

    @Test
    public void testDuplicateEntryOverriding01_01() {
        final FluentBundle bundle = FluentBundleBuilder.builder(Locale.ENGLISH)
                .addResource(resource01)
                .addResource(resource01)
                .build();

        assertEquals(1, bundle.getMessages().size());
        assertEquals(
                "Test Yay!",
                bundle.resolveMessage("entry01", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testDuplicateEntryOverriding01_02() {
        final FluentBundle bundle = FluentBundleBuilder.builder(Locale.ENGLISH)
                .addResource(resource01)
                .addResource(getResourceFromResource("duplicate/overrides/duplicate_entries01.ftl"))
                .build();

        assertEquals(1, bundle.getMessages().size());
        assertEquals(
                "Test Yay! 2",
                bundle.resolveMessage("entry01", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testDuplicateEntryOverriding02_01() {
        final FluentBundle bundle = FluentBundleBuilder.builder(Locale.ENGLISH)
                .addResource(resource02)
                .addResource(resource02)
                .build();

        assertEquals(1, bundle.getMessages().size());
        assertEquals(
                "And more things that can be written here.",
                bundle.resolveMessage("entry02", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testDuplicateEntryOverriding02_02() {
        final FluentBundle bundle = FluentBundleBuilder.builder(Locale.ENGLISH)
                .addResource(resource02)
                .addResource(getResourceFromResource("duplicate/overrides/duplicate_entries02.ftl"))
                .build();

        assertEquals(1, bundle.getMessages().size());
        assertEquals(
                "And more things that can be written here. 2",
                bundle.resolveMessage("entry02", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testNoDuplicateEntryException() {
        final FluentBundle bundle = FluentBundleBuilder.builder(Locale.ENGLISH)
                .addResource(resource01)
                .addResourceNoDup(resource02)
                .build();

        assertEquals(2, bundle.getMessages().size());
    }
}
