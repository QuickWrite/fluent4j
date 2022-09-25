package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestEscapedCharacters {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("escaped_characters.ftl");
    }

    @Test
    public void testTextBackslashOne() {
        Assertions.assertEquals("Value with \\ a backslash", GetFileHelper.getMessage(bundle, "text-backslash-one"));
    }

    @Test
    public void testTextBackslashTwo() {
        Assertions.assertEquals("Value with \\\\ two backslashes", GetFileHelper.getMessage(bundle, "text-backslash-two"));
    }

    @Test
    public void testTextBackslashBrace() {
        Assertions.assertEquals("Value with \\{placeable}", GetFileHelper.getMessage(bundle, "text-backslash-brace"));
    }

    @Test
    public void testTextBackslashU() {
        Assertions.assertEquals("\\u0041", GetFileHelper.getMessage(bundle, "text-backslash-u"));
    }

    @Test
    public void testTextBackslashBackslashU() {
        Assertions.assertEquals("\\\\u0041", GetFileHelper.getMessage(bundle, "text-backslash-backslash-u"));
    }

    @Test
    public void testQuoteInString() {
        Assertions.assertEquals("\"", GetFileHelper.getMessage(bundle, "quote-in-string"));
    }

    @Test
    public void testBackslashInString() {
        Assertions.assertEquals("\\", GetFileHelper.getMessage(bundle, "backslash-in-string"));
    }

    @Test
    public void testStringUnicode4Digits() {
        Assertions.assertEquals("\u0041", GetFileHelper.getMessage(bundle, "string-unicode-4digits"));
    }

    @Test
    public void testEscapeUnicode4Digits() {
        Assertions.assertEquals("\\u0041", GetFileHelper.getMessage(bundle, "escape-unicode-4digits"));
    }

    @Test
    public void testStringUnicode6Digits() {
        Assertions.assertEquals("\uD83D\uDE02", GetFileHelper.getMessage(bundle, "string-unicode-6digits"));
    }

    @Test
    public void testEscapeUnicode6Digits() {
        Assertions.assertEquals("\\U01F602", GetFileHelper.getMessage(bundle, "escape-unicode-6digits"));
    }

    @Test
    public void testStringTooMany4Digits() {
        Assertions.assertEquals("\u004100", GetFileHelper.getMessage(bundle, "string-too-many-4digits"));
    }

    @Test
    public void testStringTooMany6Digits() {
        Assertions.assertEquals("\uD83D\uDE0200", GetFileHelper.getMessage(bundle, "string-too-many-6digits"));
    }

    @Test
    public void testLiteralBraceOpen() {
        Assertions.assertEquals("An opening { brace.", GetFileHelper.getMessage(bundle, "brace-open"));
    }

    @Test
    public void testLiteralBraceClose() {
        Assertions.assertEquals("A closing } brace.", GetFileHelper.getMessage(bundle, "brace-close"));
    }
}
