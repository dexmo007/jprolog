package com.dexmohq.prolog.model.term;

import com.google.common.collect.Iterators;
import lombok.NonNull;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterators.singletonIterator;

class ListStructure extends Structure implements List {

    private final Term head;
    private final List tail;

    ListStructure(@NonNull Term head, @NonNull List tail) {
        super(".", head, tail);
        this.head = head;
        this.tail = tail;
    }

    static List of(Term[] tail, int index) {
        if (index >= tail.length) {
            return List.empty();
        }
        return new ListStructure(tail[index], of(tail, ++index));
    }

    static List of(Iterator<? extends Term> iterator) {
        if (!iterator.hasNext()) {
            return List.empty();
        }
        final Term head = iterator.next();
        return new ListStructure(head, of(iterator));
    }

    @Override
    public Term head() {
        return head;
    }

    @Override
    public List tail() {
        return tail;
    }

    @Override
    public int size() {
        return 1 + tail.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<Term> iterator() {
        return Iterators.concat(singletonIterator(head), tail.iterator());
    }

    @Override
    public boolean contains(Variable var) {
        return head.contains(var) || tail.contains(var);
    }

    @Override
    public List substitute(Variable var, Term substitution) {
        return new ListStructure(head.substitute(var, substitution), tail.substitute(var, substitution));
    }

    @Override
    public boolean isGroundTerm() {
        return head.isGroundTerm() && tail.isGroundTerm();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ListStructure terms = (ListStructure) o;
        return head.equals(terms.head) && tail.equals(terms.tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail);
    }

    @Override
    public String toHeadTailString() {
        return "[" + head.toString() + "|" + tail.toString() + "]";
    }

    @Override
    public String toHeadTailString(int headSize) {
        if (headSize < 1) {
            throw new IllegalArgumentException("head size must be >=1");
        }
        return "[" +
                stream().limit(headSize).map(Term::toString).collect(Collectors.joining(","))
                + "|[" +
                stream().skip(headSize).map(Term::toString).collect(Collectors.joining(","))
                + "]]";
    }

    @Override
    public String toString() {
        return "[" + stream().map(Term::toString).collect(Collectors.joining(",")) + "]";
    }
}
