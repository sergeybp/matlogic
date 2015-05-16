package com.sergeybudkov;

public class Sequence extends BinaryExpr {

    final static String[] TRUE_TRUE = Resources.seq_TRUE_TRUE;
    final static String[] TRUE_FALSE = Resources.seq_TRUE_FALSE;
    final static String[] FALSE_FALSE = Resources.seq_FALSE_FALSE;
    final static String[] FALSE_TRUE = Resources.seq_FALSE_TRUE;

    public Sequence(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    String getOperator() {
        return "->";
    }

    @Override
    protected boolean evaluate(boolean left, boolean right) {
        return !left || right;
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
