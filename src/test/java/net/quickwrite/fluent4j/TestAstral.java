package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.util.bundle.FluentBundle;
import net.quickwrite.fluent4j.util.bundle.ResourceFluentBundle;
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
        Assertions.assertEquals("\uD83D\uDE02", bundle.getMessage("face-with-tears-of-joy", null));
    }

    @Test
    public void testTetragramForCentre() {
        Assertions.assertEquals("\uD834\uDF06", bundle.getMessage("tetragram-for-centre", null));
    }

    @Test
    public void testSurrogatesInText() {
        Assertions.assertEquals("\\uD83D\\uDE02", bundle.getMessage("surrogates-in-text", null));
    }

    @Test
    public void testSurrogatesInString() {
        Assertions.assertEquals("\uD83D\uDE02", bundle.getMessage("surrogates-in-string", null));
    }

    @Test
    public void testSurrogatesInAdjacentStrings() {
        Assertions.assertEquals("\uD83D\uDE02", bundle.getMessage("surrogates-in-adjacent-strings", null));
    }

    @Test
    public void testEmojiInText() {
        Assertions.assertEquals("A face \uD83D\uDE02 with tears of joy.", bundle.getMessage("emoji-in-text", null));
    }

    @Test
    public void testEmojiInString() {
        Assertions.assertEquals("A face \uD83D\uDE02 with tears of joy.", bundle.getMessage("emoji-in-string", null));

        bundle.getMessage("hi", null);
    }
}
