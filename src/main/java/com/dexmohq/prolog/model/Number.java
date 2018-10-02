package com.dexmohq.prolog.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class Number extends Constant {

    int number;

    @Override
    public String toString() {
        return Integer.toString(number);
    }

}
