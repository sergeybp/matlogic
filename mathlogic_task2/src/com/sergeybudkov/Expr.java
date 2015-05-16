package com.sergeybudkov;

import java.util.Map;


public interface Expr {

    public abstract boolean evaluate(Map<String, Boolean> variables);
}
