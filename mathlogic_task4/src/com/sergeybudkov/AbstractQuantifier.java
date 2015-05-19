package com.sergeybudkov;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractQuantifier implements Expr {
    protected String var = "#";
    protected Expr expression;

    public AbstractQuantifier(Expr expression) {
        this.expression = expression;
    }

    public String getVar() {
        return var;
    }

    public Expr getExpression() {
        return expression;
    }

    public abstract List<Pair> pathToFirstFreeEntry(String x);
    public abstract String toStringWithReplacedVar(Term term, String var);

    protected List<Pair> pathToFirstFreeEntryImpl(String x, String operationType) {
        List<Pair> pathFromCurPos = expression.pathToFirstFreeEntry(x);

        if (!var.equals(x) && pathFromCurPos != null) {
            ((LinkedList) pathFromCurPos).addFirst(new Pair(operationType, var));
            return pathFromCurPos;
        }

        return null;
    }

    protected String toStringWithReplacedVarImpl(Term term, String var, String operationSign) {
        if (this.var.equals(var)) {
            return toString();
        } else {
            return operationSign + this.var + expression.toStringWithReplacedVar(term, var);
        }
    }

    public boolean isFreeToReplace(Term term, String var) {
        return this.var.equals(var) || !(term.pathToFirstFreeEntry(this.var) != null && expression.pathToFirstFreeEntry(var) != null) && expression.isFreeToReplace(term, var);

    }

}
