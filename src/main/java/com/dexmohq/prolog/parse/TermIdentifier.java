package com.dexmohq.prolog.parse;

import lombok.Value;

import java.util.regex.Pattern;

@Value
public class TermIdentifier {

    Pattern regex;
    TermProvider provider;

}
