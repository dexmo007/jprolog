package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.Term;

import java.util.Set;

public interface Substitution {

    Term apply(Term in);

    Set<SingleSubstitution> applyOnSubstitutions(Set<SingleSubstitution> in);

    static Substitution none() {
        return NoSubstitution.INSTANCE;
    }

}
