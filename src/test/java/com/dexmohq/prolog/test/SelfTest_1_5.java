package com.dexmohq.prolog.test;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.Unification;
import com.dexmohq.prolog.model.Structure;
import com.dexmohq.prolog.model.Term;
import com.dexmohq.prolog.model.Variable;
import com.dexmohq.prolog.substitution.Substitution;
import com.dexmohq.prolog.substitution.SubstitutionComposition;

public class SelfTest_1_5 {

    public static final Variable X = new Variable("X");
    public static final Variable Y = new Variable("Y");
    public static final Variable Z = new Variable("Z");

    public static void main(String[] args) {
        Term t1 = new Structure("z",
                new Structure("a", new Structure("b", new Variable("D"))),
                new Structure("d", new Structure("e", new Variable("F"))),
                new Structure("g", new Variable("H"))
        );
        Term t2 = new Structure("z",
                new Variable("H"),
                new Variable("K"),
                new Structure("g", new Variable("F"))
        );
        System.out.println("1.)");
        unifyAndPrintResult(t1, t2);

        System.out.println();

        t1 = new Structure("p",
                new Structure("f", new Structure("g", X)),
                Y,
                X
        );
        t2 = new Structure("p",
                Z,
                new Structure("h", X),
                new Structure("i", Z)
        );
        System.out.println("2.)");
        unifyAndPrintResult(t1, t2);
    }

    private static void unifyAndPrintResult(Term t1, Term t2) {
        Substitution substitution;
        try {
            substitution = Unification.unify(t1, t2);
            if (substitution instanceof SubstitutionComposition) {
                System.out.println(((SubstitutionComposition) substitution).simplify());
            } else {
                System.out.println(substitution);
            }
        } catch (NotUnifiableException e) {
            System.out.println("not unifiable");
        }
    }

}
