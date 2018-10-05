package com.dexmohq.prolog.model.clause;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.substitution.Substitution;

import java.util.Optional;

public interface Clause {

    Substitution unifyWith(Literal literal) throws NotUnifiableException;

    default Optional<Substitution> tryUnifyWith(Literal literal) {
        try {
            return Optional.of(unifyWith(literal));
        } catch (NotUnifiableException e) {
            return Optional.empty();
        }
    }

    Literal[] applyRight(Substitution substitution);

    Clause applyLeft(Substitution substitution);
}
