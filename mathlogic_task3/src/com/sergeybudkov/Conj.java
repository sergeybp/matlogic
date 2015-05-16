package com.sergeybudkov;

public class Conj extends BinaryExpr {

    final static String[] TRUE_TRUE = Resources.conj_TRUE_TRUE;
    final static String[] TRUE_FALSE = Resources.conj_TRUE_FALSE;
    final static String[] FALSE_TRUE = Resources.conj_FALSE_TRUE;
    final static String[] FALSE_FALSE = Resources.conj_FALSE_FALSE;

    public Conj(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    String getOperator() {
        return "&";
    }

    @Override
    protected boolean evaluate(boolean left, boolean right) {
        return left && right;
    }

    @Override
    protected String[] getSolution(boolean left, boolean right) {
        if (left) {
            if (right) {
                return TRUE_TRUE;
            } else {
                return TRUE_FALSE;
            }
        } else {
            if (right) {
                return FALSE_TRUE;
            } else {
                return FALSE_FALSE;
            }
        }
    }

}
