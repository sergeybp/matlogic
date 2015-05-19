package com.sergeybudkov;

import java.util.LinkedList;
import java.util.List;

public abstract class BinaryOperation implements Expr {
    protected Expr left;
    protected Expr right;

    public BinaryOperation(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    public abstract List<Pair> pathToFirstFreeEntry(String x);
    public abstract String toStringWithReplacedVar(Term term, String var);

    protected List<Pair> pathToFirstFreeEntryImpl(String x, String operationType) {
        List<Pair> pathFromCurPos;

        pathFromCurPos = left.pathToFirstFreeEntry(x);
        if (pathFromCurPos != null) {
            ((LinkedList) pathFromCurPos).addFirst(new Pair(operationType, "left"));
            return pathFromCurPos;
        }

        pathFromCurPos = right.pathToFirstFreeEntry(x);
        if (pathFromCurPos != null) {
            ((LinkedList) pathFromCurPos).addFirst(new Pair(operationType, "right"));
            return pathFromCurPos;
        }

        return null;
    }

    protected String toStringWithReplacedVarImpl(Term term, String var, String operationSign) {
        return "(" + left.toStringWithReplacedVar(term, var) + operationSign
                + right.toStringWithReplacedVar(term, var) + ")";
    }
}
