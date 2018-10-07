package com.dexmohq.prolog.model.clause;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.Unification;
import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.substitution.Substitution;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Factum implements Clause {

    private final Structure fact;

    public Factum(String functor, Term... terms) {
        this.fact = new Structure(functor, terms);
    }

    @Override
    public Substitution unifyWith(Literal literal) throws NotUnifiableException {
        return Unification.unify(fact, literal);
    }

    @Override
    public Literal[] applyRight(Substitution substitution) {
        return new Literal[]{new Literal(fact.getFunctor(), ((Structure) substitution.apply(fact)).getComponents())};
    }

    @Override
    public Clause applyLeft(Substitution substitution) {
        return new Factum((Structure) substitution.apply(fact));
    }

    @Override
    public String toString() {
        return fact + ".";
    }
}
