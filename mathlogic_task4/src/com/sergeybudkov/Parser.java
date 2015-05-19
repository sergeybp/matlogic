package com.sergeybudkov;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {

    private Stack<Expr> implList;

    public Parser() {
        implList = new Stack<Expr>();
    }

    public Expr parse(String s) throws MyException {
        if (s.isEmpty()) throw new MyException("String to parse is empty");
        Res<Expr> result = impl(s);
        if (!result.rest.isEmpty()) {
            throw new MyException("Error in \'" + result.rest + "\'");
        }
        implList.clear();
        return result.exp;
    }

    private Res impl(String s) throws MyException {
        Res<Expr> current = conj(s);
        int localCounter = 0;

        if (current.exp instanceof Term) {
            return current;
        }

        while (current.rest.length() > 1) {
            char sign1 = current.rest.charAt(0);
            char sign2 = current.rest.charAt(1);
            if (sign1 != '-' || sign2 != '>') {
                break;
            }
            implList.add((Expr) current.exp);
            localCounter++;

            String next = current.rest.substring(2);
            current = conj(next);
            if (current.exp instanceof Term) {
                throw new MyException("Impl had a bad argument in \'" + next + "\'");
            }
        }

        if (!implList.isEmpty() && (localCounter > 0)) {
            implList.add((Expr) current.exp);
            Expr exp = implList.pop();
            for (int i = localCounter - 1; i > -1; i--) {
                exp = Operations.createImpl(implList.pop(), exp);
            }

            return new Res<Expr>(exp, current.rest);
        }

        return new Res<Expr>(current.exp, current.rest);
    }

    private Res conj(String s) throws MyException {
        Res<Expr> current = disj(s);
        Expr exp = current.exp;

        if (exp instanceof Term) {
            return current;
        }

        while (current.rest.length() > 0) {
            char sign = current.rest.charAt(0);
            if (sign != '&') {
                break;
            }

            String next = current.rest.substring(1);
            current = disj(next);
            if (!(current.exp instanceof Term)) {
                exp = Operations.createConj(exp, current.exp);
            } else {
                throw new MyException("Conj had a bad argument\n");
            }
        }

        return new Res<Expr>(exp, current.rest);
    }

    private Res disj(String s) throws MyException {
        Res<Expr> current = unaryOperations(s);
        Expr exp = current.exp;

        if (exp instanceof Term) {
            return current;
        }

        while (current.rest.length() > 0) {
            char sign = current.rest.charAt(0);
            if (sign != '|') {
                break;
            }

            String next = current.rest.substring(1);
            current = unaryOperations(next);
            if (!(current.exp instanceof Term)) {
                exp = Operations.createDisj(exp, current.exp);
            } else {
                throw new MyException("Disj had a bad argument\n");
            }
        }

        return new Res(exp, current.rest);
    }

    private Res unaryOperations(String s) throws MyException {
        String next;
        Res<Expr> current;
        if (s.length() > 0 && s.charAt(0) == '!') {
            next = s.substring(1);
            current = unaryOperations(next);
            if (current.exp instanceof Term) {
                throw new MyException("Neg had a bad argument\n");
            } else {
                return new Res<Expr>(Operations.createNeg((Expr) current.exp), current.rest);
            }
        }

        if (s.length() > 1 && (s.charAt(0) == '@' || s.charAt(0) == '?') &&
                Character.isLetter(s.charAt(1)) && Character.isLowerCase(s.charAt(1))) {
            StringBuilder var = new StringBuilder();
            var.append(s.charAt(1));
            int i = 2;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                var.append(s.charAt(i));
                i++;
            }

            next = s.substring(var.length() + 1);
            current = unaryOperations(next);
            if (current.exp instanceof Term) {
                throw new MyException("Quantifier \'" + s.charAt(0) + "\' had a bad argument\n");
            } else {
                if (s.charAt(0) == '@') {
                    return new Res<Expr>(Quantifiers.createForall(current.exp, var.toString()), current.rest);
                } else {
                    return new Res<Expr>(Quantifiers.createExist(current.exp, var.toString()), current.rest);
                }
            }
        }

        return equality(s);
    }

    private Res equality(String s) throws MyException {
        Res<Expr> current = plus(s);
        Expr exp = current.exp;

        if (!(exp instanceof Term)) {
            return current;
        }

        if (current.rest.length() > 0 && (current.rest.charAt(0) == '=')) {
            String next = current.rest.substring(1);
            current = plus(next);
            if (current.exp instanceof Term) {
                exp = Operations.createEqual((Term) exp, (Term) current.exp);
            } else {
                throw new MyException("Equal had a bad argument\n");
            }
        }

        return new Res(exp, current.rest);
    }

    private Res plus(String s) throws MyException {
        Res current = multiply(s);
        Expr exp = (Expr) current.exp;
        if (!(exp instanceof Term)) {
            return current;
        }

        while (current.rest.length() > 0) {
            char sign = current.rest.charAt(0);
            if (sign != '+') {
                break;
            }

            String next = current.rest.substring(1);
            current = multiply(next);
            if (current.exp instanceof Term) {
                exp = Operators.createPlus((Term) exp, (Term) current.exp);
            } else {
                throw new MyException("Plus had a bad argument\n");
            }
        }

        return new Res(exp, current.rest);
    }

    private Res multiply(String s) throws MyException {
        Res current = increment(s);
        Expr exp = (Expr) current.exp;
        if (!(exp instanceof Term)) {
            return current;
        }

        while (current.rest.length() > 0) {
            char sign = current.rest.charAt(0);
            if (sign != '*') {
                break;
            }

            String next = current.rest.substring(1);
            current = increment(next);
            if (current.exp instanceof Term) {
                exp = Operators.createMul((Term) exp, (Term) current.exp);
            } else {
                throw new MyException("Mul had a bad argument\n");
            }

        }

        return new Res(exp, current.rest);
    }

    private Res increment(String s) throws MyException {
        Res current = brackets(s);
        Expr exp = (Expr) current.exp;
        if (current.exp instanceof Term) {
            while (current.rest.length() > 0 && current.rest.charAt(0) == '\'') {
                exp = Operators.createIncr((Term) exp);
                current.rest = current.rest.substring(1);
            }
        }

        return new Res(exp, current.rest);
    }

    private Res brackets(String s) throws MyException {
        if (s.length() == 0) {
            throw new MyException("Error in \"" + s + "\"");
        }

        char ch = s.charAt(0);
        if (ch == '(') {
            Res r = impl(s.substring(1));
            if (r.rest.length() > 0 && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                throw new MyException("Error in \"" + s + "\". Most probably there is not closed bracket.");
            }

            if (r.exp instanceof Term) {
                if (r.rest.length() > 0 && r.rest.charAt(0) == '\'') {
                    r.exp = Operators.createIncr((Term) r.exp);
                    r.rest = r.rest.substring(1);
                }
            }

            return r;
        }

        return predicate(s);
    }

    private Res predicate(String s) throws MyException {
        StringBuilder res = new StringBuilder();
        int i = 0;
        if (s.length() > 0 && (Character.isLetter(s.charAt(0)) &&
                               Character.isUpperCase(s.charAt(0))) ) {
            res.append(s.charAt(0));
            i++;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                res.append(s.charAt(i));
                i++;
            }
        }

        if (res.length() == 0) {
            Res<Term> result = terms(s);
            return result;
        }

        s = s.substring(res.length());

        Res<List<Term>> result;
        if (s.length() > 0 && s.charAt(0) == '(') {
            result = comma(s.substring(1));
            if (result.rest.length() > 0 && result.rest.charAt(0) == ')') {
                return new Res<Predicate>(new Predicate(res.toString(), result.exp), result.rest.substring(1));
            } else {
                throw new MyException("Error in \"" + s + "\"");
            }
        } else {
            return new Res<Predicate>(new Predicate(res.toString()), s);
        }
    }

    private Res<Term> bracketsInTerms(String s) throws MyException {
        if (s.length() == 0) {
            throw new MyException("Error in \"" + s + "\"");
        }

        char ch = s.charAt(0);
        if (ch == '(') {
            Res<Term> r = plus(s.substring(1));
            if (r.rest.length() > 0 && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                throw new MyException("Error in \"" + s + "\". Most probably there is not closed bracket.");
            }
            return new Res<Term>(Operators.createBrackets(r.exp), r.rest);
        }
        return terms(s);
    }

    private Res<Term> terms(String s) throws MyException {
        StringBuilder res = new StringBuilder();
        int i = 0;

        if (s.length() > 0 && s.charAt(0) == '0') {
            return new Res<Term>(Terms.createZero(), s.substring(1));
        }

        if (s.length() > 0 && (Character.isLetter(s.charAt(0)) &&
            Character.isLowerCase(s.charAt(0))) ) {

            res.append(s.charAt(0));
            i++;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                res.append(s.charAt(i));
                i++;
            }
        }

        if (res.length() == 0) {
            throw new MyException("Error in \"" + s + "\". Most probably some term is missed.");
        }

        s = s.substring(res.length());

        Res<List<Term>> result;
        if (s.length() > 0 && s.charAt(0) == '(') {
            result = comma(s.substring(1));
            if (result.rest.length() > 0 && result.rest.charAt(0) == ')') {
                return new Res<Term>(Terms.createTermWithArgs(res.toString(), result.exp), result.rest.substring(1));
            } else {
                throw new MyException("Error in \"" + s + "\"");
            }
        } else {
            return new Res<Term>(Terms.createVar(res.toString()), s);
        }
    }

    private Res<List<Term>> comma(String s) throws MyException {
        Res<Term> current = plus(s);
        Term exp = current.exp;

        List<Term> terms = new ArrayList();
        terms.add(exp);

        while (current.rest.length() > 0) {
            char sign = current.rest.charAt(0);
            if (sign != ',') {
                break;
            }

            String next = current.rest.substring(1);
            current = plus(next);

            terms.add(current.exp);
        }

        return new Res(terms, current.rest);
    }
}
