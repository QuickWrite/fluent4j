package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.*;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.impl.container.FluentResourceBundle;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalleeExpressionTest {
    private static final FluentBundle<ResultBuilder> bundle;
    private static final FluentBundle<ResultBuilder> bundle2;
    private static final String FUNCTION_RESULT = "[Function Result]";

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("expressions/callee/callee_expressions.ftl");
        // TODO: Replace with builder
        final FluentResourceBundle<ResultBuilder> resourceBundle = new FluentResourceBundle<>(ULocale.ENGLISH);

        resourceBundle.addResource(resource);
        resourceBundle.addFunction(new FluentFunction<>() {
            @Override
            public String getIdentifier() {
                return "FUNCTION";
            }

            @Override
            public FluentPlaceable<ResultBuilder> parseFunction(
                    final FluentScope<ResultBuilder> scope,
                    final ArgumentList<ResultBuilder> argumentList
            ) {
                return new FluentTextElement<>(FUNCTION_RESULT);
            }
        });

        bundle = resourceBundle;
        bundle2 = ResourceBundleFactory.forLocale(ULocale.ENGLISH);
        bundle2.addResource(resource);
    }

    @Test
    public void testFunctionCalleePlaceable() {
        assertEquals(
                FUNCTION_RESULT,
                bundle.resolveMessage(
                        "function-callee-placeable",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testFunctionCalleePlaceableNoFunction() {
        assertEquals(
                "{FUNCTION()}",
                bundle2.resolveMessage(
                        "function-callee-placeable",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermCalleePlaceable() {
        assertEquals(
                "{-term}",
                bundle.resolveMessage(
                        "term-callee-placeable",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageCalleeError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/message/callee_message_error.ftl")
        );
    }

    @Test
    public void testMessageMixedCaseCalleeError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/message/callee_message_mixed_case_error.ftl")
        );
    }

    @Test
    public void testMessageAttrCalleeError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/message/callee_message_attr_error.ftl")
        );
    }

    @Test
    public void testTermAttrPlaceableError() {
        // TODO: Don't use RuntimeException
        assertThrows(
                RuntimeException.class,
                () -> getResourceFromResource("expressions/callee/term/callee_term_attr_placeable_error.ftl")
        );
    }

    @Test
    public void testVariableParameterError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/variable/callee_variable_parameter_error.ftl")
        );
    }

    // Selector tests

    @Test
    public void testFunctionCalleeSelector() {
        assertEquals(
                "Value",
                bundle.resolveMessage(
                        "function-callee-selector",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermAttrCalleeSelector() {
        assertEquals(
                "Value",
                bundle.resolveMessage(
                        "term-attr-callee-selector",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageCalleeSelectorError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/message/selector/callee_message_error.ftl")
        );
    }

    @Test
    public void testMessageMixedCaseCalleeSelectorError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/message/selector/callee_message_mixed_case_error.ftl")
        );
    }

    @Test
    public void testMessageAttrCalleeSelectorError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/message/selector/callee_message_attr_error.ftl")
        );
    }

    @Test
    public void testTermSelectorError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/term/selector/callee_term_error.ftl")
        );
    }

    @Test
    public void testVariableParameterSelectorError() {
        assertThrows(
                FluentExpectedException.class,
                () -> getResourceFromResource("expressions/callee/variable/selector/callee_variable_parameter_error.ftl")
        );
    }
}
