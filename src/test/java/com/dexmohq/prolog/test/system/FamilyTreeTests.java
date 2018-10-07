package com.dexmohq.prolog.test.system;

import com.dexmohq.prolog.model.clause.Factum;
import com.dexmohq.prolog.model.clause.Literal;
import com.dexmohq.prolog.model.clause.Rule;
import com.dexmohq.prolog.model.term.Atom;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.model.term.Variable;
import com.dexmohq.prolog.system.PrologSystem;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FamilyTreeTests {

    private static final Variable X = new Variable("X");
    private static final Variable Y = new Variable("Y");
    private PrologSystem system;

    private static final Atom mother = new Atom("mother");
    private static final Atom father = new Atom("father");
    private static final Atom son = new Atom("son");
    private static final Atom daughter = new Atom("daughter");
    private static final Atom motherOfMother = new Atom("motherOfMother");
    private static final Atom fatherOfMother = new Atom("fatherOfMother");
    private static final Atom motherOfFather = new Atom("motherOfFather");
    private static final Atom fatherOfFather = new Atom("fatherOfFather");

    @Before
    public void setUp() throws Exception {
        system = new PrologSystem()
                .addClause(new Factum(parents(mother, father, son)))
                .addClause(new Factum(parents(mother, father, daughter)))
                .addClause(new Factum(parents(motherOfMother, fatherOfMother, mother)))
                .addClause(new Factum(parents(motherOfFather, fatherOfFather, father)))
        ;
    }

    private static Literal siblings(Term x, Term y) {
        return new Literal("siblings", x, y);
    }

    @Test
    public void testSiblings() {
        final Variable M = new Variable("M");
        final Variable F = new Variable("F");
        system.addClause(new Rule(siblings(X, Y),
                parents(M, F, X),
                parents(M, F, Y)));
        assertThat(system.ask(siblings(son, daughter))).isTrue();
        assertThat(system.ask(siblings(daughter, son))).isTrue();

        assertThat(system.ask(siblings(new Atom("unknown"), son))).isFalse();
        assertThat(system.ask(siblings(son, new Atom("unknown")))).isFalse();
        assertThat(system.ask(siblings(new Atom("unknown"), new Atom("unknown2")))).isFalse();
    }

    private static Literal grandfatherOf(Term grandfather, Term grandson) {
        return new Literal("grandfatherOf", grandfather, grandson);
    }

    @Test
    public void testGrandfatherOf() {
        final Variable Grandfather = new Variable("Grandfather");
        final Variable Grandson = new Variable("Grandson");
        final Variable Grandmother = new Variable("Grandmother");
        final Variable Mother = new Variable("Mother");
        final Variable Father = new Variable("Father");
        system
                .addClause(new Rule(grandfatherOf(Grandfather, Grandson),
                        parents(Grandmother, Grandfather, Father),
                        parents(Mother, Father, Grandson)))
                .addClause(new Rule(grandfatherOf(Grandfather, Grandson),
                        parents(Grandmother, Grandfather, Mother),
                        parents(Mother, Father, Grandson)
                ))
        ;
        assertThat(system.ask(grandfatherOf(fatherOfMother, son))).isTrue();
        assertThat(system.ask(grandfatherOf(fatherOfFather, son))).isTrue();
        assertThat(system.ask(grandfatherOf(fatherOfMother, daughter))).isTrue();
        assertThat(system.ask(grandfatherOf(fatherOfFather, daughter))).isTrue();

        assertThat(system.ask(grandfatherOf(fatherOfFather, father))).isFalse();
        assertThat(system.ask(grandfatherOf(fatherOfFather, mother))).isFalse();
        assertThat(system.ask(grandfatherOf(fatherOfMother, mother))).isFalse();
        assertThat(system.ask(grandfatherOf(fatherOfMother, father))).isFalse();
    }

    private static Literal parents(Term mother, Term father, Term child) {
        return new Literal("parents", mother, father, child);
    }
}
