package net.quickwrite.fluent4j.test;

import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.result.ResultBuilder;
import org.junit.jupiter.api.Test;

import static net.quickwrite.fluent4j.test.util.FluentUtils.getResourceFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommentTest {
    @Test
    public void testCommentOne() {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("comment/comment_one.ftl");

        assertEquals(0, resource.entries().size());
    }

    @Test
    public void testCommentTwo() {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("comment/comment_two.ftl");

        assertEquals(0, resource.entries().size());
    }

    @Test
    public void testCommentThree() {
        final FluentResource<ResultBuilder> resource = getResourceFromResource("comment/comment_three.ftl");

        assertEquals(0, resource.entries().size());
    }

    @Test
    public void testCommentMore() {
        assertThrows(FluentExpectedException.class, () -> getResourceFromResource("comment/comment_more.ftl"));
    }

    @Test
    public void testCommentNoWhitespace() {
        assertThrows(FluentExpectedException.class, () -> getResourceFromResource("comment/comment_no_whitespace.ftl"));
    }
}
