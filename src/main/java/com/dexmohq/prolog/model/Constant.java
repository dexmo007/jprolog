package com.dexmohq.prolog.model;

public abstract class Constant implements Term {

    public boolean contains(Variable var) {
        return false;
    }

    @Override
    public Term substitute(Variable var, Term substitution) {
        return this;
    }

}
