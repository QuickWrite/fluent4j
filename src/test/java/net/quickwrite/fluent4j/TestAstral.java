package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class TestAstral {
    private FluentBundle bundle;

    @Before
    public void setUp() throws IOException {
        this.bundle = GetFileHelper.getFluentBundle("astral.ftl");
    }

    @Test
    public void testIfExceptions() {
        Assertions.assertTrue(bundle.hasExceptions());
    }

    @Test
    public void testFaceWithTearsOfJoy() {
        Assertions.assertEquals("\uD83D\uDE02", GetFileHelper.getMessage(bundle, "face-with-tears-of-joy"));
    }

    @Test
    public void testTetragramForCentre() {
        Assertions.assertEquals("\uD834\uDF06", GetFileHelper.getMessage(bundle, "tetragram-for-centre"));
    }

    @Test
    public void testSurrogatesInText() {
        Assertions.assertEquals("\\uD83D\\uDE02", GetFileHelper.getMessage(bundle, "surrogates-in-text"));
    }

    @Test
    public void testSurrogatesInString() {
        Assertions.assertEquals("\uD83D\uDE02", GetFileHelper.getMessage(bundle, "surrogates-in-string"));
    }

    @Test
    public void testSurrogatesInAdjacentStrings() {
        Assertions.assertEquals("\uD83D\uDE02", GetFileHelper.getMessage(bundle, "surrogates-in-adjacent-strings"));
    }

    @Test
    public void testEmojiInText() {
        Assertions.assertEquals("A face \uD83D\uDE02 with tears of joy.", GetFileHelper.getMessage(bundle, "emoji-in-text"));
    }

    @Test
    public void testEmojiInString() {
        Assertions.assertEquals("A face \uD83D\uDE02 with tears of joy.", GetFileHelper.getMessage(bundle, "emoji-in-string"));
    }
}
