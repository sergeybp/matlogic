package com.sergeybudkov;

import java.util.Map;

public abstract class BinOperators implements Expr {

    protected Expr left;
    protected Expr right;

    public BinOperators(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    public Expr getLeft(){
        return left;
    }

    public Expr getRight(){
        return right;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var) {
        return apply(left.evaluate(var), right.evaluate(var));
    }

    protected abstract boolean apply(boolean leftVal, boolean rightVal);

}
