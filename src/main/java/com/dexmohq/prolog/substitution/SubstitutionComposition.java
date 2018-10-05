package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.term.Term;
import lombok.NonNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dexmohq.prolog.util.StreamUtils.reverseStream;

public class SubstitutionComposition implements Substitution {

    @NonNull
    private final LinkedList<Substitution> substitutions;

    public SubstitutionComposition() {
        this.substitutions = new LinkedList<>();
    }

    public SubstitutionComposition(List<Substitution> substitutions) {
        this.substitutions = new LinkedList<>(substitutions);
    }

    @Override
    public Term apply(Term in) {
        for (Substitution substitution : substitutions) {
            in = substitution.apply(in);
        }
        return in;
    }

    @Override
    public Set<SingleSubstitution> applyOnSubstitutions(Set<SingleSubstitution> in) {
        return this.simplify().applyOnSubstitutions(in);
    }

    @Override
    public boolean isEmpty() {
        return simplify().isEmpty();
    }

    public SubstitutionComposition addSubstitution(Substitution substitution) {
        substitutions.add(substitution);
        return this;
    }

    public Substitution simplify() {
        Set<SingleSubstitution> singleSubstitutions = new HashSet<>();
        for (Substitution substitution : substitutions) {
            singleSubstitutions = substitution.applyOnSubstitutions(singleSubstitutions);
        }
        if (singleSubstitutions.isEmpty()) {
            return Substitution.none();
        }
        return new MultiSubstitution(singleSubstitutions);
    }

    @Override
    public String toString() {
        if (substitutions.isEmpty()) {
            return "{}";
        }
        return reverseStream(substitutions)
                .map(Substitution::toString)
                .collect(Collectors.joining("°"));
    }
}
