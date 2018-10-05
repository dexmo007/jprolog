package com.dexmohq.prolog.test.unification;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.Unification;
import com.dexmohq.prolog.model.term.Atom;
import com.dexmohq.prolog.model.term.Number;
import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Variable;
import com.dexmohq.prolog.substitution.SingleSubstitution;
import com.dexmohq.prolog.substitution.Substitution;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnificationBaseCasesTests {

    private static final Variable X = new Variable("X");

    @Test
    public void testTwoEqualVariables() throws NotUnifiableException {
        final Substitution unifier = Unification.unify(X, X);
        assertThat(unifier).isEqualTo(Substitution.none());
    }

    @Test(expected = NotUnifiableException.class)
    public void testVariableContainedInOtherTerm() throws NotUnifiableException {
        Unification.unify(X, new Structure("f", X));
    }

    @Test(expected = NotUnifiableException.class)
    public void testVariableContainedInOtherTerm2() throws NotUnifiableException {
        Unification.unify(new Structure("f", X), X);
    }

    @Test
    public void testTwoEqualNumbers() throws NotUnifiableException {
        final Substitution unifier = Unification.unify(new Number(1), new Number(1));
        assertThat(unifier).isEqualTo(Substitution.none());
    }

    @Test(expected = NotUnifiableException.class)
    public void testTwoUnequalNumbers() throws NotUnifiableException {
        Unification.unify(new Number(1), new Number(2));
    }

    @Test(expected = NotUnifiableException.class)
    public void testTwoDifferentlyNamedStructures() throws NotUnifiableException {
        Unification.unify(new Structure("foo", X), new Structure("bar", X));
    }

    @Test(expected = NotUnifiableException.class)
    public void testTwoStructuresWithDifferentArities() throws NotUnifiableException {
        Unification.unify(new Structure("f", X), new Structure("f", X, X));
    }

    @Test(expected = NotUnifiableException.class)
    public void testStructureAndAtom() throws NotUnifiableException {
        Unification.unify(new Structure("f", X), new Atom("a"));
    }

    @Test
    public void testDifferentVariables() throws NotUnifiableException {
        final Substitution unifier = Unification.unify(X, new Variable("Y"));
        assertThat(unifier).isEqualTo(new SingleSubstitution(X, new Variable("Y")));
    }
}
