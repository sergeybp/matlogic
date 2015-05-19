package com.sergeybudkov;

public abstract class UnaryOperator extends Term {
    protected Term term;

    public UnaryOperator(Term term) {
        this.term = term;
    }
}
