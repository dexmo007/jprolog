package com.dexmohq.prolog.util;

import lombok.experimental.UtilityClass;

import java.util.Deque;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class StreamUtils {

    public static <E> Stream<E> reverseStream(Deque<E> linkedList) {
        final Spliterator<E> spliterator = Spliterators.spliteratorUnknownSize(linkedList.descendingIterator(), 0);
        return StreamSupport.stream(spliterator, false);
    }

}
