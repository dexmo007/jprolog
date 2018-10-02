package com.dexmohq.prolog.model;

public interface Term {

    boolean contains(Variable var);

    Term substitute(Variable var, Term substitution);
}
