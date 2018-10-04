package com.dexmohq.prolog;

import com.dexmohq.prolog.model.Constant;
import com.dexmohq.prolog.model.Structure;
import com.dexmohq.prolog.model.Term;
import com.dexmohq.prolog.model.Variable;
import com.dexmohq.prolog.substitution.SingleSubstitution;
import com.dexmohq.prolog.substitution.Substitution;
import com.dexmohq.prolog.substitution.SubstitutionComposition;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Unification {

    public static Substitution unify(Term t1, Term t2) throws NotUnifiableException {
        if (t1 instanceof Variable && t2 instanceof Variable && t1.equals(t2)) {
            return Substitution.none();
        }
        if (t1 instanceof Variable) {
            return createSingleSubstitutionIfNotContained(t2, (Variable) t1);
        }
        if (t2 instanceof Variable) {
            return createSingleSubstitutionIfNotContained(t1, (Variable) t2);
        }
        if (t1 instanceof Constant && t2 instanceof Constant) {
            if (t1.equals(t2)) {
                return Substitution.none();
            } else {
                throw new NotUnifiableException();
            }
        }
        if (t1 instanceof Structure && t2 instanceof Structure) {
            return tryUnifyStructures((Structure) t1, (Structure) t2);
        }
        throw new NotUnifiableException();
    }

    private static Substitution tryUnifyStructures(Structure s1, Structure s2) throws NotUnifiableException {
        if (!s1.getFunctor().equals(s2.getFunctor())
                || s1.getComponents().length != s2.getComponents().length) {
            throw new NotUnifiableException();
        }
        final SubstitutionComposition sigma = new SubstitutionComposition();
        for (int i = 0; i < s1.getComponents().length; i++) {
            sigma.addSubstitution(
                    unify(
                            sigma.apply(s1.getComponents()[i]), sigma.apply(s2.getComponents()[i])
                    )
            );
        }
        return sigma.simplify();

    }

    private static Substitution createSingleSubstitutionIfNotContained(Term t1, Variable t2)
            throws NotUnifiableException {
        if (!t1.contains(t2)) {
            return new SingleSubstitution(t2, t1);
        }
        throw new NotUnifiableException();
    }

}
