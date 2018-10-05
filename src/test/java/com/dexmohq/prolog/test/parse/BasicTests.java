package com.dexmohq.prolog.test.parse;

import com.dexmohq.prolog.model.term.Atom;
import com.dexmohq.prolog.model.term.List;
import com.dexmohq.prolog.model.term.Number;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.parse.TermParser;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicTests {

    @Test
    public void testEmptyList() {
        final Term parsed = TermParser.parse("[]");
        assertThat(parsed).isEqualTo(List.empty());
    }

    @Test
    public void testComma() {
        final Term parsed = TermParser.parse(",");
        assertThat(parsed).isEqualTo(new Atom(","));
    }

    @Test
    public void testSemicolon() {
        final Term parsed = TermParser.parse(";");
        assertThat(parsed).isEqualTo(new Atom(";"));
    }

    @Test
    public void testBang() {
        final Term parsed = TermParser.parse("!");
        assertThat(parsed).isEqualTo(new Atom("!"));
    }

    @Test
    public void testNumber() {
        final Term one = TermParser.parse("1");
        assertThat(one).isEqualTo(new Number(1));

        final Term oneTwo = TermParser.parse("12");
        assertThat(oneTwo).isEqualTo(new Number(12));

        final Term oneTwoThree = TermParser.parse("123");
        assertThat(oneTwoThree).isEqualTo(new Number(123));
    }

}
