package com.sergeybudkov;

import java.util.List;
import java.util.Map;

public abstract class BinaryExpr extends Expr {

    private Expr left;
    private Expr right;

    public BinaryExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    abstract String getOperator();

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + getOperator() + right + ")";
    }

    @Override
    public boolean compareToPattern(Expr pattern, Map<String, Expr> patternValues) {
        if (this == pattern) {
            return true;
        }
        if (getClass() == pattern.getClass()) {
            BinaryExpr binaryExpressionPattern = (BinaryExpr) pattern;
            return getOperator().equals(binaryExpressionPattern.getOperator())
                    && left.compareToPattern(binaryExpressionPattern.left, patternValues)
                    && right.compareToPattern(binaryExpressionPattern.right, patternValues);
        }
        return super.compareToPattern(pattern, patternValues);
    }

    @Override
    public boolean compareToExpression(Expr expression) {
        if (this == expression) {
            return true;
        }
        if (getClass() == expression.getClass()) {
            BinaryExpr binExpression = (BinaryExpr) expression;
            return getOperator().equals(binExpression.getOperator())
                    && left.compareToExpression(binExpression.left)
                    && right.compareToExpression(binExpression.right);
        }
        return false;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        return evaluate(left.evaluate(values), right.evaluate(values));
    }

    protected abstract boolean evaluate(boolean left, boolean right);

    @Override
    public void replace(Map<String, Expr> expForNamedAnyExpression) {
        if (left.getClass() == NamedAnyExpression.class) {
            NamedAnyExpression named = (NamedAnyExpression) left;
            left = expForNamedAnyExpression.get(named.name);
        } else {
            left.replace(expForNamedAnyExpression);
        }
        if (right.getClass() == NamedAnyExpression.class) {
            NamedAnyExpression named = (NamedAnyExpression) right;
            right = expForNamedAnyExpression.get(named.name);
        } else {
            right.replace(expForNamedAnyExpression);
        }
    }

    @Override
    public void addSteps(Map<String, Boolean> varValues, List<Expr> steps) {
        left.addSteps(varValues, steps);
        right.addSteps(varValues, steps);
        Expr.addSteps(getSolution(left.evaluate(varValues), right.evaluate(varValues)),
                left, right, steps);
    }

    protected abstract String[] getSolution(boolean left, boolean right);
}
