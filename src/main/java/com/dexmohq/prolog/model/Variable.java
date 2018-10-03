package com.dexmohq.prolog.model;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
public class Variable implements Term {

    @NonNull
    String name;

    public boolean contains(Variable var) {
        return name.equals(var.name);
    }

    @Override
    public Term substitute(Variable var, Term substitution) {
        if (this.equals(var)) {
            return substitution;
        }
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
