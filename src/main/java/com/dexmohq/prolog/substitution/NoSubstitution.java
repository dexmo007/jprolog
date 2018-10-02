package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.Term;
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
    public String toString() {
        return "{}";
    }
}
