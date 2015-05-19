package com.sergeybudkov;

public abstract class UnaryOperation implements Expr {
    protected Expr expression;

    public UnaryOperation(Expr expression) {
        this.expression = expression;
    }
}
