package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.ast.placeable.StringLiteral;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.util.args.FunctionFluentArgs;
import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;
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
        Assertions.assertEquals("{FUN1()}", GetFileHelper.getMessage(bundle, "valid-func-name-01"));
    }

    @Test
    public void testValidFuncName02() {
        Assertions.assertEquals("{FUN_FUN()}", GetFileHelper.getMessage(bundle, "valid-func-name-02"));
    }

    @Test
    public void testValidFuncName03() {
        Assertions.assertEquals("{FUN-FUN()}", GetFileHelper.getMessage(bundle, "valid-func-name-03"));
    }

    @Test
    public void testPositionalArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,a,{msg},n:]",
                GetFileHelper.getMessage(bundle, "positional-args")
        );
    }

    @Test
    public void testNamedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,y.Y,]",
                GetFileHelper.getMessage(bundle, "named-args")
        );
    }

    @Test
    public void testDenseNamedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,y.Y,]",
                GetFileHelper.getMessage(bundle, "dense-named-args")
        );
    }

    @Test
    public void testMixedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,a,{msg},n:x.1,y.Y,]",
                GetFileHelper.getMessage(bundle, "mixed-args")
        );
    }

    @Test
    public void testShuffledArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,a,{msg},n:x.1,y.Y,]",
                GetFileHelper.getMessage(bundle, "shuffled-args")
        );
    }

    @Test
    public void testDuplicateNamedArgs() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.X,]",
                GetFileHelper.getMessage(bundle, "duplicate-named-args")
        );
    }

    @Test
    public void testSparseInlineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,{msg},n:x.1,]",
                GetFileHelper.getMessage(bundle, "sparse-inline-call")
        );
    }

    @Test
    public void testEmptyInlineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "{FUN(void)}",
                GetFileHelper.getMessage(bundle, "empty-inline-call")
        );
    }

    @Test
    public void testMultilineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,{msg},n:x.1,]",
                GetFileHelper.getMessage(bundle, "multiline-call")
        );
    }

    @Test
    public void testSparseMultilineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,{msg},n:x.1,]",
                GetFileHelper.getMessage(bundle, "sparse-multiline-call")
        );
    }

    @Test
    public void testEmptyMultilineCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "{FUN(void)}",
                GetFileHelper.getMessage(bundle, "empty-multiline-call")
        );
    }

    @Test
    public void testUnindentedArgNumber() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,n:]",
                GetFileHelper.getMessage(bundle, "unindented-arg-number")
        );
    }

    @Test
    public void testUnindentedArgString() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:a,n:]",
                GetFileHelper.getMessage(bundle, "unindented-arg-string")
        );
    }

    @Test
    public void testUnindentedMsgRef() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{msg},n:]",
                GetFileHelper.getMessage(bundle, "unindented-arg-msg-ref")
        );
    }

    @Test
    public void testUnindentedTermRef() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{-msg},n:]",
                GetFileHelper.getMessage(bundle, "unindented-arg-term-ref")
        );
    }

    @Test
    public void testUnindentedVarRef() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{$var},n:]",
                GetFileHelper.getMessage(bundle, "unindented-arg-var-ref")
        );
    }

    @Test
    public void testUnindentedCall() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{OTHER()},n:]",
                GetFileHelper.getMessage(bundle, "unindented-arg-call")
        );
    }

    @Test
    public void testUnindentedNamedArg() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,]",
                GetFileHelper.getMessage(bundle, "unindented-named-arg")
        );
    }

    @Test
    public void testUnindentedClosingParen() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:{x},n:]",
                GetFileHelper.getMessage(bundle, "unindented-closing-paren")
        );
    }

    @Test
    public void testTrailingCommaOneArgument() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,n:]",
                GetFileHelper.getMessage(bundle, "one-argument")
        );
    }

    @Test
    public void testTrailingCommaManyArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,3,n:]",
                GetFileHelper.getMessage(bundle, "many-arguments")
        );
    }

    @Test
    public void testTrailingCommaInlineSparsArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,3,n:]",
                GetFileHelper.getMessage(bundle, "inline-sparse-args")
        );
    }

    @Test
    public void testTrailingCommaMultilineArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,n:]",
                GetFileHelper.getMessage(bundle, "mulitline-args")
        );
    }

    @Test
    public void testTrailingCommaMultilineSparseArguments() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:1,2,n:]",
                GetFileHelper.getMessage(bundle, "mulitline-sparse-args")
        );
    }

    @Test
    public void testNamedArgumentsSparse() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,y.2,z.3,]",
                GetFileHelper.getMessage(bundle, "sparse-named-arg")
        );
    }

    @Test
    public void testNamedArgumentsUnindentedColon() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,]",
                GetFileHelper.getMessage(bundle, "unindented-colon")
        );
    }

    @Test
    public void testNamedArgumentsUnindentedValue() {
        bundle.addFunction(new FunFunction());

        Assertions.assertEquals(
                "[p:n:x.1,]",
                GetFileHelper.getMessage(bundle, "unindented-value")
        );
    }

    private static class FunFunction extends AbstractFunction {
        public FunFunction() {
            super("FUN");
        }

        @Override
        public FluentPlaceable getResult(final AccessorBundle bundle, final FunctionFluentArgs arguments, int recursionDepth) {
            if (arguments.isEmpty()) {
                return new StringLiteral("{FUN(void)}");
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[p:");

            for (int i = 0; i < arguments.getPositionalSize(); i++) {
                stringBuilder.append(arguments.getPositional(i).getResult(bundle, recursionDepth - 1));
                stringBuilder.append(",");
            }

            stringBuilder.append("n:");

            for (final String key : arguments.getNamedKeys()) {
                stringBuilder.append(key);
                stringBuilder.append(".");
                stringBuilder.append(arguments.getNamed(key).getResult(bundle, recursionDepth - 1));
                stringBuilder.append(",");
            }

            stringBuilder.append("]");

            return new StringLiteral(stringBuilder.toString());
        }
    }
}
