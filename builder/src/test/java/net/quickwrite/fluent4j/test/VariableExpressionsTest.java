package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.ArgumentListBuilder;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariableExpressionsTest {
    private static final FluentBundle bundle;

    private static final ArgumentList arguments_string = ArgumentListBuilder.builder()
            .add("var", "String")
            .build();

    private static final ArgumentList arguments_number = ArgumentListBuilder.builder()
            .add("var", 42)
            .build();

    static {
        final FluentResource resource = getResourceFromResource("expressions/variables/variable_expressions.ftl");
        bundle = FluentBundleBuilder.builder(ULocale.ENGLISH).addResource(resource).build();
    }

    @Test
    public void testKey01NoValue() {
        assertEquals(
                "{$var}",
                bundle.resolveMessage(
                        "key01",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey01StringValue() {
        assertEquals(
                "String",
                bundle.resolveMessage(
                        "key01",
                        arguments_string,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey01NumberValue() {
        assertEquals(
                "42",
                bundle.resolveMessage(
                        "key01",
                        arguments_number,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey02NoValue() {
        assertEquals(
                "{$var}",
                bundle.resolveMessage(
                        "key02",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey02StringValue() {
        assertEquals(
                "String",
                bundle.resolveMessage(
                        "key02",
                        arguments_string,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey02NumberValue() {
        assertEquals(
                "42",
                bundle.resolveMessage(
                        "key02",
                        arguments_number,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey03NoValue() {
        assertEquals(
                "{$var}",
                bundle.resolveMessage(
                        "key03",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey03StringValue() {
        assertEquals(
                "String",
                bundle.resolveMessage(
                        "key03",
                        arguments_string,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey03NumberValue() {
        assertEquals(
                "42",
                bundle.resolveMessage(
                        "key03",
                        arguments_number,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey04NoValue() {
        assertEquals(
                "{$var}",
                bundle.resolveMessage(
                        "key04",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey04StringValue() {
        assertEquals(
                "String",
                bundle.resolveMessage(
                        "key04",
                        arguments_string,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testKey04NumberValue() {
        assertEquals(
                "42",
                bundle.resolveMessage(
                        "key04",
                        arguments_number,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMissingIdentifier() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/variables/variable_missing_identifier.ftl")
        );
    }

    @Test
    public void testDoubleDollar() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/variables/variable_double_dollar.ftl")
        );
    }

    @Test
    public void testInvalidFirstChar() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/variables/variable_invalid_first_char.ftl")
        );
    }
}
