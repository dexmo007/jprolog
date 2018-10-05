package com.dexmohq.prolog.model.term;

public interface Term {

    boolean contains(Variable var);

    Term substitute(Variable var, Term substitution);

    boolean isGroundTerm();
}
