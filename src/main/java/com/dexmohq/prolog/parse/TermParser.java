package com.dexmohq.prolog.parse;

import com.dexmohq.prolog.model.term.*;
import com.dexmohq.prolog.model.term.Number;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@UtilityClass
public class TermParser {

    private static final Set<TermIdentifier> identifiers = Set.of(
            new TermIdentifier(Pattern.compile("^\\[]$"), args -> List.empty()),
            new TermIdentifier(Pattern.compile("^,$"), args -> new Atom(",")),
            new TermIdentifier(Pattern.compile("^!$"), args -> new Atom("!")),
            new TermIdentifier(Pattern.compile("^;$"), args -> new Atom(";")),
            new TermIdentifier(Pattern.compile("^(\\d+)$"), args -> new Number(Integer.parseInt((String) args[0]))),
            new TermIdentifier(Pattern.compile("^([a-z][a-zA-Z\\d_]*)$"), args -> new Atom((String) args[0])),
            new TermIdentifier(Pattern.compile("^([+\\-*/<=>'\\\\:.?@#$&^~]+)$"), args -> new Atom((String) args[0])),
            new TermIdentifier(Pattern.compile("^('(?:[^']|'')+')$"), args -> new Atom((String) args[0])),
            new TermIdentifier(Pattern.compile("^((?:[A-Z]|_)[a-zA-Z\\d_]*)$"), args -> new Variable((String) args[0]))
    );

    public static Term parse(String s) {
        s = s.replaceAll("\\s", "");
        for (TermIdentifier identifier : identifiers) {
            final String[] matches = identifier.getRegex()
                    .matcher(s)
                    .results()
                    .map(MatchResult::group)
                    .toArray(String[]::new);
            if (matches.length > 0) {
                return identifier.getProvider().create((Object[]) matches);
            }
        }
        throw new IllegalArgumentException("nothing matched");
    }

}
