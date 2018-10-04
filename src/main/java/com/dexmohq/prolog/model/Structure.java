package com.dexmohq.prolog.model;

import lombok.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Structure implements Term {

    @NonNull
    private final String functor;

    @NonNull
    private final Term[] components;

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

    public int arity() {
        return components.length;
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
