package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.term.Term;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class MultiSubstitution implements Substitution {

    private final Set<SingleSubstitution> substitutions;

    @Override
    public Term apply(Term in) {
        for (SingleSubstitution substitution : substitutions) {
            in = substitution.apply(in);
        }
        return in;
    }

    @Override
    public Set<SingleSubstitution> applyOnSubstitutions(Set<SingleSubstitution> in) {
        Set<SingleSubstitution> singleSubstitutions = in.stream()
                .map(s -> new SingleSubstitution(s.getSource(), this.apply(s.getTarget())))
                .collect(toSet());
        singleSubstitutions.addAll(substitutions);
        return singleSubstitutions;
    }

    @Override
    public boolean isEmpty() {
        return substitutions.isEmpty();
    }

    @Override
    public Substitution dropAnonymous() {
        final Set<SingleSubstitution> singleSubstitutions = substitutions.stream()
                .filter(s -> !s.getSource().isAnonymous())
                .collect(toSet());
        if (singleSubstitutions.isEmpty()) {
            return Substitution.none();
        }
        if (singleSubstitutions.size() == 1) {
            return singleSubstitutions.iterator().next();
        }
        return new MultiSubstitution(singleSubstitutions);
    }

    @Override
    public String toString() {
        return "{" +
                substitutions.stream()
                        .map(SingleSubstitution::innerToString)
                        .collect(joining(","))
                + "}";
    }
}
