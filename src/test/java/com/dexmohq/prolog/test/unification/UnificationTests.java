package com.dexmohq.prolog.test.unification;

import com.dexmohq.prolog.NotUnifiableException;
import com.dexmohq.prolog.Unification;
import com.dexmohq.prolog.model.Atom;
import com.dexmohq.prolog.model.Structure;
import com.dexmohq.prolog.model.Variable;
import org.junit.Test;

public class UnificationTests {

    private static final Variable X = new Variable("X");

    @Test(expected = NotUnifiableException.class)
    public void testNotUnifiable_Inequality() throws NotUnifiableException {
        final Structure t1 = new Structure("p", X, X);
        final Structure t2 = new Structure("p", new Atom("a"), new Atom("b"));
        Unification.unify(t1, t2);
    }


}
