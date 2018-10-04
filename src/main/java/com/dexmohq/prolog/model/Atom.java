package com.dexmohq.prolog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
