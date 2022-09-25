package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestNumbers {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("numbers.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testIntZero() {
        Assertions.assertEquals("0", GetFileHelper.getMessage(bundle, "int-zero"));
    }

    @Test
    public void testIntPositive() {
        Assertions.assertEquals("1", GetFileHelper.getMessage(bundle, "int-positive"));
    }

    @Test
    public void testIntNegative() {
        Assertions.assertEquals("-1", GetFileHelper.getMessage(bundle, "int-negative"));
    }

    @Test
    public void testIntNegativeZero() {
        Assertions.assertEquals("0", GetFileHelper.getMessage(bundle, "int-negative-zero"));
    }

    @Test
    public void testIntPositivePadded() {
        Assertions.assertEquals("1", GetFileHelper.getMessage(bundle, "int-positive-padded"));
    }

    @Test
    public void testIntNegativePadded() {
        Assertions.assertEquals("-1", GetFileHelper.getMessage(bundle, "int-negative-padded"));
    }

    @Test
    public void testIntZeroPadded() {
        Assertions.assertEquals("0", GetFileHelper.getMessage(bundle, "int-zero-padded"));
    }

    @Test
    public void testIntNegativeZeroPadded() {
        Assertions.assertEquals("0", GetFileHelper.getMessage(bundle, "int-negative-zero-padded"));
    }

    @Test
    public void testFloatZero() {
        Assertions.assertEquals("0", GetFileHelper.getMessage(bundle, "float-zero"));
    }

    @Test
    public void testFloatPositive() {
        Assertions.assertEquals("0.01", GetFileHelper.getMessage(bundle, "float-positive"));
    }

    @Test
    public void testFloatPositiveOne() {
        Assertions.assertEquals("1.03", GetFileHelper.getMessage(bundle, "float-positive-one"));
    }

    @Test
    public void testFloatPositiveWithoutFraction() {
        Assertions.assertEquals("1", GetFileHelper.getMessage(bundle, "float-positive-without-fraction"));
    }

    @Test
    public void testFloatNegative() {
        Assertions.assertEquals("-0.01", GetFileHelper.getMessage(bundle, "float-negative"));
    }

    @Test
    public void testFloatNegativeOne() {
        Assertions.assertEquals("-1.03", GetFileHelper.getMessage(bundle, "float-negative-one"));
    }

    @Test
    public void testFloatNegativeWithoutFraction() {
        Assertions.assertEquals("-1", GetFileHelper.getMessage(bundle, "float-negative-without-fraction"));
    }

    @Test
    public void testFloatPositivePaddedLeft() {
        Assertions.assertEquals("1.03", GetFileHelper.getMessage(bundle, "float-positive-padded-left"));
    }

    @Test
    public void testFloatPositivePaddedRight() {
        Assertions.assertEquals("1.03", GetFileHelper.getMessage(bundle, "float-positive-padded-right"));
    }

    @Test
    public void testFloatPositivePaddedBoth() {
        Assertions.assertEquals("1.03", GetFileHelper.getMessage(bundle, "float-positive-padded-both"));
    }

    @Test
    public void testFloatNegativePaddedLeft() {
        Assertions.assertEquals("-1.03", GetFileHelper.getMessage(bundle, "float-negative-padded-left"));
    }

    @Test
    public void testFloatNegativePaddedRight() {
        Assertions.assertEquals("-1.03", GetFileHelper.getMessage(bundle, "float-negative-padded-right"));
    }

    @Test
    public void testFloatNegativePaddedBoth() {
        Assertions.assertEquals("-1.03", GetFileHelper.getMessage(bundle, "float-negative-padded-both"));
    }
}
