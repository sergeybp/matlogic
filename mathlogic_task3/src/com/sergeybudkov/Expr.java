package com.sergeybudkov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Expr {

    public boolean compareToPattern(Expr pattern, Map<String, Expr> patternValues) {
        if (pattern.getClass() == NamedAnyExpression.class) {
            NamedAnyExpression namedExpression = (NamedAnyExpression) pattern;
            Expr expression = patternValues.get(namedExpression.getName());
            if (expression != null) {
                return compareToExpression(expression);
            } else {
                patternValues.put(namedExpression.getName(), this);
                return true;
            }
        }
        return false;
    }

    public abstract boolean compareToExpression(Expr expression);

    public abstract boolean evaluate(Map<String, Boolean> values);

    public abstract void replace(Map<String, Expr> expForNamedAnyExpression);

    public abstract void addSteps(Map<String, Boolean> varValues, List<Expr> steps);

    protected static void addSteps(String[] solution, Expr A, Expr B, List<Expr> steps) {
        PatternParser parser = new PatternParser();
        for (String str : solution) {
            Expr exp = parser.parseExpression(str);
            Map<String, Expr> map = new HashMap<String, Expr>();
            map.put("A", A);
            map.put("B", B);
            exp.replace(map);
            if (exp.getClass() == NamedAnyExpression.class) {
                exp = map.get(((NamedAnyExpression) exp).name);
            }
            steps.add(exp);
        }
    }
}
