package com.dexmohq.prolog.model;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Value
@AllArgsConstructor
public class Structure implements Term {

    String functor;

    @NonNull
    Term[] components;

    public Structure(String functor, Term term) {
        this.functor = functor;
        this.components = new Term[]{term};
    }

    public Structure(String functor, Term term, @NonNull Term... terms) {
        this.functor = functor;
        this.components = new Term[terms.length + 1];
        this.components[0] = term;
        System.arraycopy(terms, 0, this.components, 1, terms.length);
    }

    @Override
    public boolean contains(Variable var) {
        return stream(components)
                .anyMatch(term -> term.contains(var));
    }

    @Override
    public Term substitute(Variable var, Term substitution) {
        return new Structure(functor, stream(components)
                .map(c -> c.substitute(var, substitution))
                .toArray(Term[]::new));
    }

    @Override
    public String toString() {
        return functor + "(" +
                stream(components)
                        .map(Term::toString)
                        .collect(joining(","))
                + ")";
    }
}
