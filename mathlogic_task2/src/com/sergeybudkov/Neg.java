package com.sergeybudkov;

import java.util.Map;


public class Neg implements Expr {
    private Expr expr;

    public Neg(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var) {
        return !expr.evaluate(var);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Neg) {
            Neg c = (Neg) o;
            return (expr.equals(c.expr));
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "(" + "!" + expr.toString() + ")";
    }
}
