package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.ast.placeable.StringLiteral;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FunctionFluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;
import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestCallExpressions {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("call_expressions.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testValidFuncName01() {
        Assertions.assertEquals("{FUN1()}", bundle.getMessage("valid-func-name-01", null));
    }

    @Test
    public void testValidFuncName02() {
        Assertions.assertEquals("{FUN_FUN()}", bundle.getMessage("valid-func-name-02", null));
    }

    @Test
    public void testValidFuncName03() {
        Assertions.assertEquals("{FUN-FUN()}", bundle.getMessage("valid-func-name-03", null));
    }

    @Test
    public void testPositionalArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,a,{msg},n:]",
                bundle.getMessage("positional-args", null)
        );
    }

    @Test
    public void testNamedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,y.Y,]",
                bundle.getMessage("named-args", null)
        );
    }

    @Test
    public void testDenseNamedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,y.Y,]",
                bundle.getMessage("dense-named-args", null)
        );
    }

    @Test
    public void testMixedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,a,{msg},n:x.1,y.Y,]",
                bundle.getMessage("mixed-args", null)
        );
    }

    @Test
    public void testShuffledArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,a,{msg},n:x.1,y.Y,]",
                bundle.getMessage("shuffled-args", null)
        );
    }

    @Test
    public void testDuplicateNamedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.X,]",
                bundle.getMessage("duplicate-named-args", null)
        );
    }

    @Test
    public void testSparseInlineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,{msg},n:x.1,]",
                bundle.getMessage("sparse-inline-call", null)
        );
    }

    @Test
    public void testEmptyInlineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "{FUN(void)}",
                bundle.getMessage("empty-inline-call", null)
        );
    }

    @Test
    public void testMultilineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,{msg},n:x.1,]",
                bundle.getMessage("multiline-call", null)
        );
    }

    @Test
    public void testSparseMultilineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,{msg},n:x.1,]",
                bundle.getMessage("sparse-multiline-call", null)
        );
    }

    @Test
    public void testEmptyMultilineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "{FUN(void)}",
                bundle.getMessage("empty-multiline-call", null)
        );
    }

    @Test
    public void testUnindentedArgNumber() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,n:]",
                bundle.getMessage("unindented-arg-number", null)
        );
    }

    @Test
    public void testUnindentedArgString() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,n:]",
                bundle.getMessage("unindented-arg-string", null)
        );
    }

    @Test
    public void testUnindentedMsgRef() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{msg},n:]",
                bundle.getMessage("unindented-arg-msg-ref", null)
        );
    }

    @Test
    public void testUnindentedTermRef() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{-msg},n:]",
                bundle.getMessage("unindented-arg-term-ref", null)
        );
    }

    @Test
    public void testUnindentedVarRef() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{$var},n:]",
                bundle.getMessage("unindented-arg-var-ref", null)
        );
    }

    @Test
    public void testUnindentedCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{OTHER()},n:]",
                bundle.getMessage("unindented-arg-call", null)
        );
    }

    @Test
    public void testUnindentedNamedArg() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,]",
                bundle.getMessage("unindented-named-arg", null)
        );
    }

    @Test
    public void testUnindentedClosingParen() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{x},n:]",
                bundle.getMessage("unindented-closing-paren", null)
        );
    }

    @Test
    public void testTrailingCommaOneArgument() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,n:]",
                bundle.getMessage("one-argument", null)
        );
    }

    @Test
    public void testTrailingCommaManyArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,3,n:]",
                bundle.getMessage("many-arguments", null)
        );
    }

    @Test
    public void testTrailingCommaInlineSparsArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,3,n:]",
                bundle.getMessage("inline-sparse-args", null)
        );
    }

    @Test
    public void testTrailingCommaMultilineArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,n:]",
                bundle.getMessage("mulitline-args", null)
        );
    }

    @Test
    public void testTrailingCommaMultilineSparseArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,n:]",
                bundle.getMessage("mulitline-sparse-args", null)
        );
    }

    @Test
    public void testNamedArgumentsSparse() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,y.2,z.3,]",
                bundle.getMessage("sparse-named-arg", null)
        );
    }

    @Test
    public void testNamedArgumentsUnindentedColon() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,]",
                bundle.getMessage("unindented-colon", null)
        );
    }

    @Test
    public void testNamedArgumentsUnindentedValue() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,]",
                bundle.getMessage("unindented-value", null)
        );
    }

    private static class FunFunction extends AbstractFunction {
        public FunFunction() {
            super("FUN");
        }

        @Override
        public FluentPlaceable getResult(final DirectFluentBundle bundle, final FunctionFluentArgs arguments) {
            if (arguments.isEmpty()) {
                return new StringLiteral("{FUN(void)}");
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[p:");

            for (int i = 0; i < arguments.getPositionalSize(); i++) {
                stringBuilder.append(arguments.getPositional(i).getResult(bundle, arguments));
                stringBuilder.append(",");
            }

            stringBuilder.append("n:");

            for (final String key : arguments.getNamedKeys()) {
                stringBuilder.append(key);
                stringBuilder.append(".");
                stringBuilder.append(arguments.getNamed(key).getResult(bundle, arguments));
                stringBuilder.append(",");
            }

            stringBuilder.append("]");

            return new StringLiteral(stringBuilder.toString());
        }
    }
}
