package com.sergeybudkov;

import java.util.List;
import java.util.Map;

public class Var extends Expr {

    public final String name;

    public Var(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Var variable = (Var) o;

        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean compareToPattern(Expr pattern, Map<String, Expr> patternValues) {
        if (pattern.getClass() == getClass()) {
            return compareToExpression(pattern);
        }
        return super.compareToPattern(pattern, patternValues);
    }

    @Override
    public boolean compareToExpression(Expr expression) {
        if (expression.getClass() != getClass()) {
            return false;
        }
        Var variable = (Var) expression;
        return name.equals(variable.getName());
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        return values.get(name);
    }

    @Override
    public void replace(Map<String, Expr> expForNamedAnyExpression) {
    }

    @Override
    public void addSteps(Map<String, Boolean> varValues, List<Expr> steps) {
        steps.add(evaluate(varValues) ?
                this :
                new Neg(this)
        );
    }
}
