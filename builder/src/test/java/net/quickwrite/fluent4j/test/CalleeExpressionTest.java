package net.quickwrite.fluent4j.test;

import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalleeExpressionTest {
    private static final FluentBundle bundle;
    private static final FluentBundle bundle2;
    private static final String FUNCTION_RESULT = "[Function Result]";

    static {
        final FluentResource resource = getResourceFromResource("expressions/callee/callee_expressions.ftl");

        bundle = FluentBundleBuilder.builder(Locale.ENGLISH)
                .addResource(resource)
                .addFunction(new FluentFunction() {
                    @Override
                    public String getIdentifier() {
                        return "FUNCTION";
                    }

                    @Override
                    public FluentPlaceable parseFunction(
                            final FluentScope scope,
                            final ArgumentList argumentList
                    ) {
                        return new FluentTextElement(FUNCTION_RESULT);
                    }
                }).build();

        bundle2 = FluentBundleBuilder.builder(Locale.ENGLISH).addResource(resource).build();
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
        assertThrows(
                FluentBuilderException.class,
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
