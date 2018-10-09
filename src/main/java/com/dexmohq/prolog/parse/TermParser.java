package com.dexmohq.prolog.parse;

import com.dexmohq.prolog.model.term.*;
import com.dexmohq.prolog.model.term.Number;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class TermParser {

    private static final Set<TermIdentifier> identifiers = Set.of(
            new TermIdentifier(Pattern.compile("^\\[]$"), matcher -> List.empty()),
            new TermIdentifier(Pattern.compile("^,$"), matcher -> new Atom(",")),
            new TermIdentifier(Pattern.compile("^!$"), matcher -> new Atom("!")),
            new TermIdentifier(Pattern.compile("^;$"), matcher -> new Atom(";")),
            new TermIdentifier(Pattern.compile("^(\\d+)$"), matcher -> new Number(Integer.parseInt(matcher.group(1)))),
            new TermIdentifier(Pattern.compile("^([a-z][a-zA-Z\\d_]*)$"), matcher -> new Atom(matcher.group(1))),
            new TermIdentifier(Pattern.compile("^([+\\-*/<=>'\\\\:.?@#$&^~]+)$"), matcher -> new Atom(matcher.group(1))),
            new TermIdentifier(Pattern.compile("^('(?:[^']|'')+')$"), matcher -> new Atom(matcher.group(1))),
            new TermIdentifier(Pattern.compile("^((?:[A-Z]|_)[a-zA-Z\\d_]*)$"), matcher -> new Variable(matcher.group(1))),
            new TermIdentifier(Pattern.compile("^([a-z][a-zA-Z\\d_]*)\\((.*)\\)$"), new StructureParser())
    );

    public static Term parse(String s) {
        s = s.replaceAll("\\s", "");// todo space in quotes?
        for (TermIdentifier identifier : identifiers) {
            final Matcher matcher = identifier.getRegex()
                    .matcher(s);
            if (matcher.matches()) {
                return identifier.getProvider().create(matcher);
            }
        }
        throw new ParseException("nothing matched");
    }

}
