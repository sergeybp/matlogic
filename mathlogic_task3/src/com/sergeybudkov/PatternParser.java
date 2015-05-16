package com.sergeybudkov;

import com.google.common.base.Preconditions;

public class PatternParser extends Parser {

    protected Expr createNamed(String name) {
        Preconditions.checkState(name.matches("[A-Z][0-9]*"), "Incorrect variable name: " + name);
        return new NamedAnyExpression(name);
    }

}
