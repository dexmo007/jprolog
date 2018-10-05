package com.dexmohq.prolog.model.term;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Getter
public class Atom extends Constant {

    @NonNull
    private final String name;

    @Override
    public String toString() {
        return name;
    }

}
