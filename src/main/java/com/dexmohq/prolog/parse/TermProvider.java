package com.dexmohq.prolog.parse;

import com.dexmohq.prolog.model.term.Term;

import java.util.regex.Matcher;

@FunctionalInterface
interface TermProvider {
    Term create(Matcher matcher);
}
