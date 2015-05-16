package com.sergeybudkov;

public class Parser {
    private String expr;

    public Parser(String expr) {
        this.expr = expr.replaceAll("->", ">");
    }

    private Expr parse(int begin, int end) {

        int balance = 0;
        for (int i = begin; i < end; ++i) {
            if (expr.charAt(i) == '(') {
                balance++;
            }
            if (expr.charAt(i) == ')') {
                balance--;
            }
            if (expr.charAt(i) == '>' && balance == 0) {
                return new Impl(parse(begin, i), parse(i + 1, end));
            }
        }

        balance = 0;
        for (int i = begin; i < end; ++i) {
            if (expr.charAt(i) == '(') {
                balance++;
            }
            if (expr.charAt(i) == ')') {
                balance--;
            }
            if (expr.charAt(i) == '|' && balance == 0) {
                return new Disj(parse(begin, i), parse(i + 1, end));
            }
        }

        balance = 0;
        for (int i = begin; i < end; ++i) {
            if (expr.charAt(i) == '(') {
                balance++;
            }
            if (expr.charAt(i) == ')') {
                balance--;
            }
            if (expr.charAt(i) == '&' && balance == 0) {
                return new Conj(parse(begin, i), parse(i + 1, end));
            }
        }

        if (expr.charAt(begin) == '!') {
            return new Neg(parse(begin + 1, end));
        }
        if (Character.isAlphabetic(expr.charAt(begin))){
            String s = "";
            int i = begin;
            while(i < end && (Character.isAlphabetic(expr.charAt(i)) || Character.isDigit(expr.charAt(i)))){
                s += expr.charAt(i);
                ++i;
            }
            return new Var(s);
        }
        if (expr.charAt(begin) == '('){
            return parse(begin + 1, end - 1);
        }

        return null;
    }

    public Expr parse() {
        return parse(0, expr.length());
    }
}