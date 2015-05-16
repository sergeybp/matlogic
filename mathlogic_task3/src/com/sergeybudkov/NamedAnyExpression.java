package com.sergeybudkov;

import java.util.Map;


public class NamedAnyExpression extends Var {

    public NamedAnyExpression(String name) {
        super(name);
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        throw new UnsupportedOperationException("Patterns can not be evaluated!");
    }
}
