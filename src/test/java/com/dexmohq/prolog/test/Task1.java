package com.dexmohq.prolog.test;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.Unification;
import com.dexmohq.prolog.model.Atom;
import com.dexmohq.prolog.model.Number;
import com.dexmohq.prolog.model.Structure;
import com.dexmohq.prolog.model.Variable;
import com.dexmohq.prolog.substitution.MultiSubstitution;
import com.dexmohq.prolog.substitution.Substitution;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Task1 {

    public static final Variable X = new Variable("X");
    public static final Atom a = new Atom("a");
    public static final Atom b = new Atom("b");
    public static final Variable Y = new Variable("Y");
    public static final Variable Z = new Variable("Z");

    @Test
    public void test1_1() throws NotUnifiableException {
        final Structure t1 = new Structure("f",
                X,
                a,
                new Structure("g", Y)
        );

        final Structure t2 = new Structure("f",
                b,
                Y,
                Z
        );
        final Substitution unifier = Unification.unify(t1, t2);
        System.out.println(unifier);
    }

    @Test
    public void test1_2() throws NotUnifiableException {
        final Structure t1 = new Structure(".",
                a,
                new Structure(".", b, new Structure("f", X))
        );

        final Structure t2 = new Structure(".",
                X,
                new Structure(".", Y, Z)
        );
        final Substitution unifier = Unification.unify(t1, t2);

        assertThat(unifier).isInstanceOf(MultiSubstitution.class);
    }

    @Test(expected = NotUnifiableException.class)
    public void test1_3() throws NotUnifiableException {
        final Structure t1 = new Structure(".",
                a,
                new Structure(".", b, new Structure("f", X))
        );
        final Structure t2 = new Structure(".",
                X,
                new Structure(".", Y, new Structure("g", a)));
        final Substitution unifier = Unification.unify(t1, t2);
        System.out.println(unifier);
    }

    @Test(expected = NotUnifiableException.class)
    public void test1_4() throws NotUnifiableException {
        final Structure t1 = new Structure(".",
                a,
                new Structure(".", b, new Structure("f", Z))
        );
        final Structure t2 = new Structure(".",
                X,
                new Structure(".", Y, Z));
        final Substitution unifier = Unification.unify(t1, t2);
        System.out.println(unifier);
    }

    @Test(expected = NotUnifiableException.class)
    public void test1_5() throws NotUnifiableException {
        Unification.unify(
          new Number(42),
          new Structure("*", new Number(6), new Number(7))
        );
    }
}
