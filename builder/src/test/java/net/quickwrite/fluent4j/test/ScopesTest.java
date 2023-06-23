package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.ArgumentListBuilder;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScopesTest {
    private static final FluentBundle<ResultBuilder> bundle;
    private static final FluentBundle<ResultBuilder> termParamBundle;
    private static final ArgumentList<ResultBuilder> argument_list1 = ArgumentListBuilder.builder()
            .add("var1", "Test")
            .build();

    private static final ArgumentList<ResultBuilder> argument_list2 = ArgumentListBuilder.builder()
            .add("var2", 2)
            .build();

    private static final ArgumentList<ResultBuilder> argument_list3 = ArgumentListBuilder.builder()
            .add("var1", "Test")
            .add("var2", 2)
            .build();

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("scope/scopes.ftl");
        bundle = FluentBundleBuilder.builder(ULocale.ENGLISH).addResource(resource).build();

        final FluentResource<ResultBuilder> termParamResource = getResourceFromResource("scope/term_parameters.ftl");
        termParamBundle = FluentBundleBuilder.builder(ULocale.ENGLISH).addResource(termParamResource).build();
    }

    @Test
    public void testTopScopeNoValues() {
        assertEquals(
                "{$var1} {$var2}",
                bundle.resolveMessage(
                        "top-scope",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTopScopeOneValue() {
        assertEquals(
                "Test {$var2}",
                bundle.resolveMessage(
                        "top-scope",
                        argument_list1,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTopScopeOneValue2() {
        assertEquals(
                "{$var1} 2",
                bundle.resolveMessage(
                        "top-scope",
                        argument_list2,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTopScopeBothValues() {
        assertEquals(
                "Test 2",
                bundle.resolveMessage(
                        "top-scope",
                        argument_list3,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope1NoValues() {
        assertEquals(
                "{$var1} {$var2}",
                bundle.resolveMessage(
                        "message-scope1",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope1OneValue() {
        assertEquals(
                "Test {$var2}",
                bundle.resolveMessage(
                        "message-scope1",
                        argument_list1,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope1OneValue2() {
        assertEquals(
                "{$var1} 2",
                bundle.resolveMessage(
                        "message-scope1",
                        argument_list2,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope1BothValues() {
        assertEquals(
                "Test 2",
                bundle.resolveMessage(
                        "message-scope1",
                        argument_list3,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope2NoValues() {
        assertEquals(
                "{$var1} {$var2}",
                bundle.resolveMessage(
                        "message-scope2",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope2OneValue() {
        assertEquals(
                "Test {$var2}",
                bundle.resolveMessage(
                        "message-scope2",
                        argument_list1,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope2OneValue2() {
        assertEquals(
                "{$var1} 2",
                bundle.resolveMessage(
                        "message-scope2",
                        argument_list2,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testMessageScope2BothValues() {
        assertEquals(
                "Test 2",
                bundle.resolveMessage(
                        "message-scope2",
                        argument_list3,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermScope1NoValues() {
        assertEquals(
                "{$var1} {$var2}",
                bundle.resolveMessage(
                        "term-scope1",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermScope1OneValue() {
        assertEquals(
                "{$var1} {$var2}",
                bundle.resolveMessage(
                        "term-scope1",
                        argument_list1,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermScope1OneValue2() {
        assertEquals(
                "{$var1} {$var2}",
                bundle.resolveMessage(
                        "term-scope1",
                        argument_list2,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermScope1BothValues() {
        assertEquals(
                "{$var1} {$var2}",
                bundle.resolveMessage(
                        "term-scope1",
                        argument_list3,
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    /*
     * Test Term Parameters
     */
    @Test
    public void testTermParametersKey01() {
        assertEquals(
                "Value",
                termParamBundle.resolveMessage(
                        "key01",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermParametersKey02() {
        assertEquals(
                "Value",
                termParamBundle.resolveMessage(
                        "key02",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermParametersKey03() {
        assertEquals(
                "Value",
                termParamBundle.resolveMessage(
                        "key03",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }

    @Test
    public void testTermParametersKey04() {
        assertEquals(
                "Value",
                termParamBundle.resolveMessage(
                        "key04",
                        StringResultFactory.construct()
                ).get().toString()
        );
    }
}
