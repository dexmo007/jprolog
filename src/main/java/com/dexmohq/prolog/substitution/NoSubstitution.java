package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.term.Term;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoSubstitution implements Substitution {

    static final Substitution INSTANCE = new NoSubstitution();

    @Override
    public Term apply(Term in) {
        return in;
    }

    @Override
    public Set<SingleSubstitution> applyOnSubstitutions(Set<SingleSubstitution> in) {
        return in;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Substitution dropAnonymous() {
        return this;
    }

    @Override
    public String toString() {
        return "{}";
    }
}
