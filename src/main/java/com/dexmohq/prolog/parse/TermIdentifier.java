package com.dexmohq.prolog.parse;

import com.dexmohq.prolog.model.term.Term;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class TermIdentifier {

    Pattern regex;
    TermProvider provider;

    @FunctionalInterface
    interface TermProvider {
        Term create(Object... args);
    }

}
