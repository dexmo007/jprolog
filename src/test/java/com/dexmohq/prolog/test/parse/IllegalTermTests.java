package com.dexmohq.prolog.test.parse;

import com.dexmohq.prolog.parse.ParseException;
import com.dexmohq.prolog.parse.TermParser;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class IllegalTermTests {

    private final String s;

    @Parameters(name = "{0}")
    public static List<String> params() {
        return Arrays.asList(
            "!!", "["
        );
    }

    @Test(expected = ParseException.class)
    public void test() {
        TermParser.parse(s);
    }
}
