package com.dexmohq.prolog.model.clause;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.Unification;
import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.substitution.Substitution;
import lombok.Value;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Value
public class Rule implements Clause {

    Literal conclusion;
    Literal[] premise;

    public Rule(Literal conclusion, Literal... premise) {
        this.conclusion = conclusion;
        this.premise = premise;
    }

    @Override
    public Substitution unifyWith(Literal literal) throws NotUnifiableException {
        return Unification.unify(conclusion, literal);
    }

    @Override
    public Literal[] applyRight(Substitution substitution) {
        return stream(premise).map(substitution).map(term -> Literal.from((Structure) term)).toArray(Literal[]::new);
    }

    @Override
    public Clause applyLeft(Substitution substitution) {
        return new Rule(Literal.from((Structure) substitution.apply(conclusion)), applyRight(substitution));
    }

    @Override
    public String toString() {
        return conclusion + " :- " + stream(premise).map(Literal::toString).collect(joining(",")) + ".";
    }
}
