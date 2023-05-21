package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.container.ResourceBundleFactory;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpressionsTest {
    private static final FluentBundle<ResultBuilder> bundle;

    static {
        bundle = ResourceBundleFactory.forLocale(ULocale.ENGLISH);
        bundle.addResource(getResourceFromResource("expressions/literal_expressions.ftl"));
        bundle.addResource(getResourceFromResource("expressions/member_expressions.ftl"));
    }

    @Test
    public void testStringExpression() {
        assertEquals(
                "abc",
                bundle.resolveMessage("string-expression", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testNumberExpression() {
        assertEquals(
                "123",
                bundle.resolveMessage("number-expression", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testNegativeNumberExpression() {
        assertEquals(
                "-42",
                bundle.resolveMessage("negative-number-expression", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testFloatExpression() {
        assertEquals(
                "3.141",
                bundle.resolveMessage("float-expression", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testNegativeFloatExpression() {
        assertEquals(
                "-6.282",
                bundle.resolveMessage("negative-float-expression", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testMessageAttributeExpressionPlaceable() {
        assertEquals(
                "{msg.attr}",
                bundle.resolveMessage("message-attribute-expression-placeable", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testTermAttributeExpressionPlaceable() {
        // TODO: Better Exception
        assertThrows(RuntimeException.class, () -> getResourceFromResource("expressions/term_attribute_expression_placeable.ftl"));
    }

    @Test
    public void testTermAttributeExpressionSelector() {
        assertEquals(
                "Value",
                bundle.resolveMessage("term-attribute-expression-selector", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testMessageAttributeExpressionSelector() {
        assertThrows(FluentExpectedException.class, () -> getResourceFromResource("expressions/message_attribute_expression_selector.ftl"));
    }
}
