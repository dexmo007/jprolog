package com.dexmohq.prolog.test.util;

import com.dexmohq.prolog.util.StreamUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamUtilsTests {

    @SafeVarargs
    private static <T> LinkedList<T> linkedList(T... ts) {
        return new LinkedList<>(Arrays.asList(ts));
    }

    @Test
    public void testReverseStream() {
        assertThat(StreamUtils.reverseStream(linkedList(1, 2))).containsExactly(2, 1);
        assertThat(StreamUtils.reverseStream(linkedList(1, 2, 3))).containsExactly(3, 2, 1);
        assertThat(StreamUtils.reverseStream(linkedList(1, 2, 3, 4))).containsExactly(4, 3, 2, 1);
    }
}
