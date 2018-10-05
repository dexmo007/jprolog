package com.dexmohq.prolog.test.model;

import com.dexmohq.prolog.model.term.Atom;
import com.dexmohq.prolog.model.term.List;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.model.term.Variable;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ListTests {

    private static final Variable X = new Variable("X");
    private static final Variable Y = new Variable("Y");
    private static final Variable Z = new Variable("Z");
    private static final Atom a = new Atom("a");

    @Test
    public void testEmpty() {
        assertThat(List.empty().isEmpty()).isTrue();
        assertThat(List.empty().size()).isEqualTo(0);
        assertThat(List.empty()).isEqualTo(List.empty());
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> List.empty().head());
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> List.empty().tail());
    }

    @Test
    public void testSingleton() {
        final List singleton = List.of(X);
        assertThat(singleton.size()).isEqualTo(1);
        assertThat(singleton.isEmpty()).isFalse();
        assertThat(singleton.head()).isEqualTo(X);
        assertThat(singleton.tail()).isEqualTo(List.empty());
        assertThat(singleton.contains(new Variable("X"))).isTrue();
        assertThat(singleton.contains(X)).isTrue();
        assertThat(singleton.substitute(X, a)).isEqualTo(List.of(a));
        assertThat(singleton).isEqualTo(List.of(X));
    }

    @Test
    public void testListOfTwo() {
        List listOfTwo = List.of(X, X);
        assertThat(listOfTwo.size()).isEqualTo(2);
        assertThat(listOfTwo.isEmpty()).isFalse();
        assertThat(listOfTwo.head()).isEqualTo(X);
        assertThat(listOfTwo.tail()).isEqualTo(List.of(X));
        assertThat(listOfTwo.tail().tail()).isEqualTo(List.empty());
        assertThat(listOfTwo).isEqualTo(List.of(X, X));
        assertThat(listOfTwo.contains(new Variable("X"))).isTrue();
        assertThat(listOfTwo.contains(X)).isTrue();
        assertThat(listOfTwo.substitute(X, a)).isEqualTo(List.of(a, a));
        Iterator<Term> iterator = listOfTwo.iterator();
        assertThat(iterator.next()).isEqualTo(X);
        assertThat(iterator.next()).isEqualTo(X);
        assertThat(iterator.hasNext()).isFalse();
        assertThat(listOfTwo.stream().collect(Collectors.toSet())).isEqualTo(Set.of(X));

        listOfTwo = List.of(X, Y);
        assertThat(listOfTwo.size()).isEqualTo(2);
        assertThat(listOfTwo.isEmpty()).isFalse();
        assertThat(listOfTwo.head()).isEqualTo(X);
        assertThat(listOfTwo.tail()).isEqualTo(List.of(Y));
        assertThat(listOfTwo.tail().tail()).isEqualTo(List.empty());
        assertThat(listOfTwo).isEqualTo(List.of(X, Y));
        assertThat(listOfTwo.contains(new Variable("X"))).isTrue();
        assertThat(listOfTwo.contains(X)).isTrue();
        assertThat(listOfTwo.contains(new Variable("Y"))).isTrue();
        assertThat(listOfTwo.contains(Y)).isTrue();
        assertThat(listOfTwo.substitute(X, a)).isEqualTo(List.of(a, Y));
        iterator = listOfTwo.iterator();
        assertThat(iterator.next()).isEqualTo(X);
        assertThat(iterator.next()).isEqualTo(Y);
        assertThat(iterator.hasNext()).isFalse();
        assertThat(listOfTwo.stream().collect(Collectors.toSet())).isEqualTo(Set.of(X, Y));


    }

    @Test
    public void testCreator_0() {
        assertThat(List.of()).isEqualTo(List.empty());
        assertThat(List.of(Collections.emptyList())).isEqualTo(List.empty());
        assertThat(List.of(Collections.emptyIterator())).isEqualTo(List.empty());
    }

    @Test
    public void testCreator_1() {
        final List expected = List.of(X, List.empty());
        assertThat(List.of(X)).isEqualTo(expected);
        assertThat(List.of(Collections.singleton(X))).isEqualTo(expected);
    }

    @Test
    public void testCreator_2() {
        final List expected = List.of(X, List.of(Y, List.empty()));
        assertThat(List.of(X, Y)).isEqualTo(expected);
        assertThat(List.of(Arrays.asList(X, Y))).isEqualTo(expected);
    }

    @Test
    public void testCollector() {
        assertThat(Stream.<Term>empty().collect(List.toTermList())).isEqualTo(List.empty());
        assertThat(Stream.of(X).collect(List.toTermList())).isEqualTo(List.of(X));
        assertThat(Stream.of(X, Y).collect(List.toTermList())).isEqualTo(List.of(X, Y));
        assertThat(Stream.of(X, Y, Z).collect(List.toTermList())).isEqualTo(List.of(X, Y, Z));
        assertThat(Stream.of(X, Y, Z, a).collect(List.toTermList())).isEqualTo(List.of(X, Y, Z, a));

        assertThat(Stream.of(X, Y).parallel().collect(List.toTermList())).containsExactlyInAnyOrder(X, Y);
        assertThat(Stream.of(X, Y, Z).parallel().collect(List.toTermList())).containsExactlyInAnyOrder(X, Y, Z);
        assertThat(Stream.of(X, Y, Z, a).parallel().collect(List.toTermList())).containsExactlyInAnyOrder(X, Y, Z, a);
    }
}
