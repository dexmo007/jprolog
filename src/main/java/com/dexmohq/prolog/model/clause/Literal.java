package com.dexmohq.prolog.model.clause;

import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Term;

public class Literal extends Structure {


    public Literal(String functor, Term[] components) {
        super(functor, components);
    }

    public static Literal from(Structure structure) {
        return new Literal(structure.getFunctor(), structure.getComponents());
    }
}
