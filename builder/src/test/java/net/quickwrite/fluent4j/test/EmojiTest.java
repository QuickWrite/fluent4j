package net.quickwrite.fluent4j.test;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentBundleBuilder;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.result.ResultBuilder;
import net.quickwrite.fluent4j.result.StringResultFactory;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmojiTest {
    private static final FluentBundle<ResultBuilder> bundle;

    static {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("utf8/emoji.ftl");
        bundle = FluentBundleBuilder.builder(ULocale.ENGLISH).addResource(resource).build();
    }

    @Test
    public void testFaceWithTearsOfYoy() {
        // Should result in "😂"
        assertEquals(
                "\uD83D\uDE02",
                bundle.resolveMessage("face-with-tears-of-joy", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testTetragramForCentre() {
        // Should result in "𝌆"
        assertEquals(
                "\uD834\uDF06",
                bundle.resolveMessage("tetragram-for-centre", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testSurrogatesInText() {
        // Should not escape anything
        assertEquals(
                "\\uD83D\\uDE02",
                bundle.resolveMessage("surrogates-in-text", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testSurrogatesInString() {
        // Should now show the emoji
        assertEquals(
                "\uD83D\uDE02",
                bundle.resolveMessage("surrogates-in-string", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testSurrogatesInAdjacentStrings() {
        assertEquals(
                "\uD83D\uDE02",
                bundle.resolveMessage("surrogates-in-adjacent-strings", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testEmojiInText() {
        assertEquals(
                "A face \uD83D\uDE02 with tears of joy.",
                bundle.resolveMessage("emoji-in-text", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testEmojiInString() {
        assertEquals(
                "A face \uD83D\uDE02 with tears of joy.",
                bundle.resolveMessage("emoji-in-string", StringResultFactory.construct()).get().toString()
        );
    }

    @Test
    public void testEmojiIdentifier() {
        // Should throw as an identifier cannot contain an emoji
        assertThrows(FluentBuilderException.class, () -> getResourceFromResource("utf8/emoji_identifier.ftl"));
    }

    @Test
    public void testEmojiExpression() {
        // Should throw as there is no parser for this emoji
        assertThrows(FluentBuilderException.class, () -> getResourceFromResource("utf8/emoji_expression.ftl"));
    }

    @Test
    public void testEmojiVariantKey() {
        // Should throw as an identifier cannot contain an emoji
        assertThrows(FluentBuilderException.class, () -> getResourceFromResource("utf8/emoji_variant_key.ftl"));
    }
}
