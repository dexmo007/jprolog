package com.dexmohq.prolog;

import com.dexmohq.prolog.model.Constant;
import com.dexmohq.prolog.model.Structure;
import com.dexmohq.prolog.model.Term;
import com.dexmohq.prolog.model.Variable;
import com.dexmohq.prolog.substitution.SubstitutionComposition;
import com.dexmohq.prolog.substitution.SingleSubstitution;
import com.dexmohq.prolog.substitution.Substitution;

public class Unification {

    public static Substitution unify(Term t1, Term t2) throws NotUnifiableException {
        if (t1 instanceof Variable && t2 instanceof Variable && t1.equals(t2)) {
            return Substitution.none();
        }
        if (t1 instanceof Variable) {
            if (!t2.contains((Variable) t1)) {
                return new SingleSubstitution((Variable) t1, t2);
            } else {
                throw new NotUnifiableException();
            }
        }
        if (t2 instanceof Variable) {
            if (!t1.contains((Variable) t2)) {
                return new SingleSubstitution((Variable) t2, t1);
            } else {
                throw new NotUnifiableException();
            }
        }
        if (t1 instanceof Constant && t2 instanceof Constant) {
            if (t1.equals(t2)) {
                return Substitution.none();
            } else {
                throw new NotUnifiableException();
            }
        }
        if (t1 instanceof Structure && t2 instanceof Structure) {
            final Structure s1 = (Structure) t1;
            final Structure s2 = (Structure) t2;
            if (s1.getFunctor().equals(s2.getFunctor()) && s1.getComponents().length == s2.getComponents().length) {
                final SubstitutionComposition sigma = new SubstitutionComposition();
                for (int i = 0; i < s1.getComponents().length; i++) {
                    sigma.addSubstitution(
                            unify(
                                    sigma.apply(s1.getComponents()[i]), sigma.apply(s2.getComponents()[i])
                            )
                    );
                }
                return sigma;
            } else {
                throw new NotUnifiableException();
            }
        }
        throw new NotUnifiableException();
    }

}
