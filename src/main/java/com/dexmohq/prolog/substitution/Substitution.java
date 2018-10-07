package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.term.Term;

import java.util.Set;
import java.util.function.Function;

public interface Substitution extends Function<Term, Term> {

    Term apply(Term in);

    Set<SingleSubstitution> applyOnSubstitutions(Set<SingleSubstitution> in);

    static Substitution none() {
        return NoSubstitution.INSTANCE;
    }

    boolean isEmpty();

    Substitution dropAnonymous();
}
