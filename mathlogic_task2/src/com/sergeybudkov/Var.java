package com.sergeybudkov;

import java.util.Map;


public class Var implements Expr {

    private String name;

    public Var(String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> var) {
        return var.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Var) {
            Var v = (Var) o;
            return v.name.equals(name);
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return name;
    }

}
