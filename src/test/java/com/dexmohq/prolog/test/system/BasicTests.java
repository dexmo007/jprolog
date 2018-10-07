package com.dexmohq.prolog.test.system;

import com.dexmohq.prolog.model.clause.Factum;
import com.dexmohq.prolog.model.clause.Literal;
import com.dexmohq.prolog.model.clause.Rule;
import com.dexmohq.prolog.model.term.*;
import com.dexmohq.prolog.system.PrologSystem;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicTests {

    private static final Variable X = new Variable("X");
    private static final Variable Y = new Variable("Y");

    private PrologSystem system;

    @Before
    public void setUp() {
        system = new PrologSystem();
    }

    private static Literal p(Atom a) {
        return new Literal("p", a);
    }

    @Test(expected = StackOverflowError.class)
    public void testInfiniteLoop() {
        final Atom a = new Atom("a");
        system
                .addClause(new Rule(p(a), p(a)))
                .addClause(new Factum(p(a)));
        system.ask(p(a));
    }

    private static Literal last(Term list, Term lastElement) {
        return new Literal("last", list, lastElement);
    }

    @Test
    public void testLastElementOfList() {
        final Variable R = new Variable("R");
        final Variable E = new Variable("E");
        system
                .addClause(new Rule(last(new Structure(".", new Variable("_"), R), E), last(R, E)))
                .addClause(new Factum(last(List.of(E), E)));
        final Atom a = new Atom("a");
        final Atom b = new Atom("b");
        final Atom c = new Atom("c");

        assertThat(system.ask(last(List.of(a), a))).isTrue();

        assertThat(system.ask(last(List.of(a, b, c), c))).isTrue();
    }

    private static final Atom susi = new Atom("susi");
    private static final Atom hans = new Atom("hans");
    private static final Atom gabi = new Atom("gabi");
    private static final Atom franz = new Atom("franz");

    private static Literal married(Term term1, Term term2) {
        return new Literal("married", term1, term2);
    }

    @Test(expected = StackOverflowError.class)
    public void testMarried_RuleFirst_Negative() {
        system
                .addClause(new Rule(married(X, Y), married(Y, X)))
                .addFact(married(susi, hans))
                .addFact(married(gabi, franz))
        ;
        system.ask(married(susi, franz));
    }

    @Test(expected = StackOverflowError.class)
    public void testMarried_RuleFirst_Positive() {
        system
                .addClause(new Rule(married(X, Y), married(Y, X)))
                .addFact(married(susi, hans))
                .addFact(married(gabi, franz))
        ;
        system.ask(married(susi, hans));
    }

    @Test(expected = StackOverflowError.class)
    public void testMarried_RuleLast_Negative() {
        system
                .addFact(married(susi, hans))
                .addFact(married(gabi, franz))
                .addClause(new Rule(married(X, Y), married(Y, X)))
        ;
        system.ask(married(susi, franz));
    }

    @Test
    public void testMarried_RuleLast_Positive() {
        system
                .addFact(married(susi, hans))
                .addFact(married(gabi, franz))
                .addClause(new Rule(married(X, Y), married(Y, X)))
        ;
        assertThat(system.ask(married(susi, hans))).isTrue();
    }
}
