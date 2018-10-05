package com.dexmohq.prolog.model.term;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class Number extends Constant {

    int underlying;

    @Override
    public String toString() {
        return Integer.toString(underlying);
    }

}
