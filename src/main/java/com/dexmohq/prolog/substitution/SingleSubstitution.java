package com.dexmohq.prolog.substitution;

import com.dexmohq.prolog.model.Term;
import com.dexmohq.prolog.model.Variable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class SingleSubstitution implements Substitution {

    private final Variable source;
    private final Term target;

    @Override
    public Term apply(Term in) {
        return in.substitute(source, target);
    }

    @Override
    public Set<SingleSubstitution> applyOnSubstitutions(Set<SingleSubstitution> in) {
        final Set<SingleSubstitution> out = in.stream()
                .map(s -> new SingleSubstitution(s.getSource(), this.apply(s.getTarget())))
                .collect(Collectors.toSet());
        out.add(this);
        return out;
    }

    public String innerToString() {
        return source.getName() + "/" + target;
    }

    @Override
    public String toString() {
        return "{" + innerToString() + "}";
    }
}
