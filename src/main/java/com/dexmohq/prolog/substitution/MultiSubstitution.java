package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.Term;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

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
                .collect(Collectors.toSet());
        singleSubstitutions.addAll(substitutions);
        return singleSubstitutions;
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
