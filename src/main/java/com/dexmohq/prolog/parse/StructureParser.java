package com.dexmohq.prolog.parse;

import com.dexmohq.prolog.model.term.Structure;
import com.dexmohq.prolog.model.term.Term;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StructureParser implements TermProvider {

    private static final Pattern ARG_DELIMITER_REGEX = Pattern.compile(",(?![^(]*[)])");

    @Override
    public Term create(Matcher matcher) {
        final String functor = matcher.group(1);
        final String argContent = matcher.group(2);
        if (argContent.isEmpty()) {
            throw new ParseException("structure cannot have zero components");
        }
        final String[] arguments = ARG_DELIMITER_REGEX.split(argContent);

        final Term[] components = Arrays.stream(arguments)
                .map(TermParser::parse)
                .toArray(Term[]::new);
        return new Structure(functor, components);
    }
}
