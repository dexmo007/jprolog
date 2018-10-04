package com.dexmohq.prolog.test;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.Unification;
import com.dexmohq.prolog.model.Atom;
import com.dexmohq.prolog.model.Number;
import com.dexmohq.prolog.model.Structure;
import com.dexmohq.prolog.model.Variable;
import com.dexmohq.prolog.substitution.MultiSubstitution;
import com.dexmohq.prolog.substitution.SingleSubstitution;
import com.dexmohq.prolog.substitution.Substitution;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class Task1 {

    private static final Variable X = new Variable("X");
    private static final Atom a = new Atom("a");
    private static final Atom b = new Atom("b");
    private static final Variable Y = new Variable("Y");
    private static final Variable Z = new Variable("Z");

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
        assertThat(unifier).isEqualTo(new MultiSubstitution(
                Set.of(
                        new SingleSubstitution(X, b),
                        new SingleSubstitution(Y, a),
                        new SingleSubstitution(Z, new Structure("g", a)))
        ));
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

        assertThat(unifier).isEqualTo(new MultiSubstitution(Set.of(
                new SingleSubstitution(X, a),
                new SingleSubstitution(Y, b),
                new SingleSubstitution(Z, new Structure("f", a))
        )));
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
        Unification.unify(t1, t2);
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
        Unification.unify(t1, t2);
    }

    @Test(expected = NotUnifiableException.class)
    public void test1_5() throws NotUnifiableException {
        Unification.unify(
                new Number(42),
                new Structure("*", new Number(6), new Number(7))
        );
    }
}
