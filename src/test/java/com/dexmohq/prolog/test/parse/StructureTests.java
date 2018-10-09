package com.dexmohq.prolog.test.parse;

import com.dexmohq.prolog.model.term.Atom;
import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.parse.ParseException;
import com.dexmohq.prolog.parse.TermParser;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StructureTests {

    @Test(expected = ParseException.class)
    public void testEmptyStructure() {
        TermParser.parse("foo()");
    }

    @Test
    public void testBasic() {
        final Term structure = TermParser.parse("foo(bar)");
        assertThat(structure).isInstanceOf(Structure.class);
        assertThat(((Structure) structure).getFunctor()).isEqualTo("foo");
        assertThat(((Structure) structure).getComponents()).isEqualTo(new Term[]{new Atom("bar")});
    }

    @Test
    public void testBasic2() {
        final Term structure = TermParser.parse("foo(bar,john)");
        assertThat(structure).isInstanceOf(Structure.class);
        assertThat(((Structure) structure).getFunctor()).isEqualTo("foo");
        assertThat(((Structure) structure).getComponents()).isEqualTo(new Term[]{new Atom("bar"), new Atom("john")});
    }
}
