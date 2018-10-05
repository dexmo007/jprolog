package com.dexmohq.prolog.system;

import com.dexmohq.prolog.model.clause.Clause;
import com.dexmohq.prolog.model.clause.Factum;
import com.dexmohq.prolog.model.clause.Literal;
import com.dexmohq.prolog.model.term.Atom;
import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.model.term.Variable;
import com.dexmohq.prolog.substitution.Substitution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class PrologSystem {

    private final List<Clause> clauses = new ArrayList<>();

    public List<Clause> findMatchingClauses(Literal requestLiteral) {
        return clauses.stream()
                .filter(clause -> clause.tryUnifyWith(requestLiteral).isPresent())
                .collect(toList());
    }

    public boolean ask(Literal request) {
        return ask(request, this.clauses);
    }

    private static boolean ask(Literal request, List<Clause> clauses) {
        for (Clause clause : clauses) {
            final Optional<Substitution> substitution = clause.tryUnifyWith(request);
            if (substitution.isPresent()) {
                if (substitution.get().isEmpty()) {
                    return true;
                }
                final Literal[] premise = clause.applyRight(substitution.get());
                if (Arrays.stream(premise).allMatch(l -> ask(l, clauses.stream().map(c -> c.applyLeft(substitution.get())).collect(toList())))) {
                    return true;
                }
            }
        }
        return false;
    }

    public PrologSystem addClause(Clause clause) {
        clauses.add(clause);
        return this;
    }

    public static void main(String[] args) {
        final PrologSystem system = new PrologSystem()
                .addClause(new Factum(new Structure("male", new Variable("X"))));
        System.out.println(system.ask(new Literal("male", new Term[]{new Atom("henrik")})));
    }

}
