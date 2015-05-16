package com.sergeybudkov;

public class Impl extends BinOperators {

    public Impl(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    protected boolean apply(boolean leftVal, boolean rightVal) {
        return !(leftVal && !rightVal);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Impl) {
            Impl c = (Impl) o;
            return (left.equals(c.left) && right.equals(c.right));
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "(" + left.toString() + "->" + right.toString() + ")";
    }
}
