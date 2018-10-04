package com.dexmohq.prolog.model;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface List extends Term, Iterable<Term> {

    Term head();

    List tail();

    int size();

    boolean isEmpty();

    @Override
    List substitute(Variable var, Term substitution);

    default Stream<Term> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    static List empty() {
        return EmptyList.INSTANCE;
    }

    static List of(Term head, List tail) {
        return new ListStructure(head, tail);
    }

    static List of(@NonNull Term... terms) {
        return ListStructure.of(terms, 0);
    }

    static List of(@NonNull Iterable<? extends Term> iterable) {
        return ListStructure.of(iterable.iterator());
    }

    static List of(@NonNull Iterator<? extends Term> iterator) {
        return ListStructure.of(iterator);
    }

    static Collector<Term, ArrayList<Term>, List> toTermList() {
        return new Collector<>() {
            @Override
            public Supplier<ArrayList<Term>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<ArrayList<Term>, Term> accumulator() {
                return ArrayList::add;
            }

            @Override
            public BinaryOperator<ArrayList<Term>> combiner() {
                return (l, r) -> {
                    l.addAll(r);
                    return l;
                };
            }

            @Override
            public Function<ArrayList<Term>, List> finisher() {
                return List::of;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.emptySet();
            }
        };
    }

}
