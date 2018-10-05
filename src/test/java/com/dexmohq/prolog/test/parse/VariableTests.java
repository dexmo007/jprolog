package com.dexmohq.prolog.test.parse;

import com.dexmohq.prolog.model.term.Term;
import com.dexmohq.prolog.model.term.Variable;
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
public class VariableTests {
    private final String s;

    @Parameters(name = "{0}")
    public static List<String> params() {
        return Arrays.asList(
                "X", "Xy", "_a", "_9"
        );
    }

    @Test
    public void test() {
        final Term parsed = TermParser.parse(s);
        Assertions.assertThat(parsed).isEqualTo(new Variable(s));
    }
}
