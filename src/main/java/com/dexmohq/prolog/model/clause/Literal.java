package com.dexmohq.prolog.model.clause;

import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.substitution.Substitution;
import lombok.NonNull;

public class Literal extends Structure {


    public Literal(String functor, @NonNull Term... components) {
        super(functor, components);
    }

    public Literal apply(Substitution substitution) {
        final Structure structure = (Structure) substitution.apply(this);
        return new Literal(structure.getFunctor(), structure.getComponents());
    }

    public static Literal from(Structure structure) {
        return new Literal(structure.getFunctor(), structure.getComponents());
    }
}
