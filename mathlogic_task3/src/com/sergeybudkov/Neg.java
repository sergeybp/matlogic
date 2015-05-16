package com.sergeybudkov;

import java.util.List;
import java.util.Map;


public class Neg extends Expr {

    final static String[] TRUE = Resources.neg_TRUE;
    final static String[] FALSE = Resources.EMPTY;

    public Expr expression;

    public Neg(Expr expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "!" + expression;
    }

    @Override
    public boolean compareToPattern(Expr pattern, Map<String, Expr> patternValues) {
        if (pattern.getClass() == getClass()) {
            Neg negationPattern = (Neg) pattern;
            return expression.compareToPattern(negationPattern.expression, patternValues);
        }
        return super.compareToPattern(pattern, patternValues);
    }

    @Override
    public boolean compareToExpression(Expr expression) {
        return expression.getClass() == Neg.class && this.expression.compareToExpression(((Neg) expression).expression);
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        return !expression.evaluate(values);
    }

    @Override
    public void replace(Map<String, Expr> expForNamedAnyExpression) {
        if (expression.getClass() == NamedAnyExpression.class) {
            NamedAnyExpression named = (NamedAnyExpression) expression;
            expression = expForNamedAnyExpression.get(named.name);
        } else {
            expression.replace(expForNamedAnyExpression);
        }
    }

    @Override
    public void addSteps(Map<String, Boolean> varValues, List<Expr> steps) {
        expression.addSteps(varValues, steps);
        Expr.addSteps(expression.evaluate(varValues) ? TRUE : FALSE, expression, null, steps);
    }
}
