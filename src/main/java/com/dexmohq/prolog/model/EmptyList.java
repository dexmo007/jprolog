package com.dexmohq.prolog.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

class EmptyList extends Atom implements List {

    static final EmptyList INSTANCE = new EmptyList();

    private EmptyList() {
        super("[]");
    }

    @Override
    public Term head() {
        throw new NoSuchElementException("head of empty list");
    }

    @Override
    public List tail() {
        throw new UnsupportedOperationException("tail of empty list");
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Iterator<Term> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public List substitute(Variable var, Term substitution) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return o instanceof EmptyList && this == o;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
