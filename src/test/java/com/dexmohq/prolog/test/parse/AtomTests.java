package com.dexmohq.prolog.test.parse;

import com.dexmohq.prolog.model.term.Atom;
import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.parse.TermParser;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class AtomTests {

    private final String s;

    @Parameters(name = "{0}")
    public static List<String> params() {
        return Arrays.asList(
                "a", "aB", "a1", "a_",
                "+", "-", "*", "/", "<", "=", ">", "'", "\\", ":", ".", "?", "@", "#", "$", "&", "^", "~",
                "+-", "<=", ">=", "&&&",
                "'a'", "''''", "'A'", "'6'"
        );
    }

    @Test
    public void test() {
        final Term parsed = TermParser.parse(s);
        Assertions.assertThat(parsed).isEqualTo(new Atom(s));
    }
}
