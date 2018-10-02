package com.dexmohq.prolog.model;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class Atom extends Constant {

    @NonNull
    String name;

    @Override
    public String toString() {
        return name;
    }
}
