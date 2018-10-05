package com.dexmohq.prolog.model.term;

public abstract class Constant implements Term {

    public boolean contains(Variable var) {
        return false;
    }

    @Override
    public Term substitute(Variable var, Term substitution) {
        return this;
    }

    @Override
    public boolean isGroundTerm() {
        return true;
    }
}
