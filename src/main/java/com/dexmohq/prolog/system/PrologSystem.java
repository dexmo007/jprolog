package com.dexmohq.prolog.system;

import com.dexmohq.prolog.model.clause.Clause;
import com.dexmohq.prolog.model.clause.Factum;
import com.dexmohq.prolog.model.clause.Literal;
import com.dexmohq.prolog.model.clause.Rule;
import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.substitution.Substitution;
import com.dexmohq.prolog.substitution.SubstitutionComposition;

import java.util.ArrayList;
import java.util.Collections;
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
        return !ask(request, this.clauses).isEmpty();
    }

    private static List<Substitution> ask(Literal request, List<Clause> clauses) {
        final ArrayList<Substitution> possibilities = new ArrayList<>();
        for (Clause clause : clauses) {
            final Optional<Substitution> substitution = clause.tryUnifyWith(request);
            if (!substitution.isPresent()) {
                continue;
            }
            if (clause instanceof Factum) {
                possibilities.add(substitution.get());
                return possibilities;
            } else if (clause instanceof Rule) {
                possibilities.addAll(proveRule((Rule) clause, substitution.get(), clauses));
            }
        }
        return possibilities;
    }

    private static List<Substitution> proveRule(Rule rule, Substitution precedingSubstitution, List<Clause> clauses) {
        final Literal[] premise = rule.applyRight(precedingSubstitution.dropAnonymous());

        List<Substitution> possibleSubstitutions = Collections.singletonList(Substitution.none());
        for (Literal literal : premise) {
            if (possibleSubstitutions.isEmpty()) {
                return Collections.emptyList();
            }
            final ArrayList<Substitution> next = new ArrayList<>();
            for (Substitution possibleSubstitution : possibleSubstitutions) {
                final List<Substitution> ask = ask(literal.apply(possibleSubstitution), clauses);
                if (ask.isEmpty()) {
                    continue;
                }
                next.addAll(ask.stream()
                        .map(a -> new SubstitutionComposition()
                                .addSubstitution(possibleSubstitution)
                                .addSubstitution(a)
                                .simplify())
                        .collect(toList()));

            }
            possibleSubstitutions = next;
        }
        return possibleSubstitutions;
    }

    public PrologSystem addClause(Clause clause) {
        clauses.add(clause);
        return this;
    }

    public PrologSystem addFact(Structure fact) {
        return addClause(new Factum(fact));
    }

    public PrologSystem addFact(String functor, Term... terms) {
        return addClause(new Factum(functor, terms));
    }

}
