package com.sergeybudkov;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Operations {

    public static class Conj extends BinaryOperation {
        public Conj(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public String toString() {
            return "(" + left.toString() + "&" + right.toString() + ")";
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            return pathToFirstFreeEntryImpl(x, Resources.CONJUCNTION);
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return toStringWithReplacedVarImpl(term, var, "&");
        }

        public boolean isFreeToReplace(Term term, String var) {
            return (left.isFreeToReplace(term, var) && right.isFreeToReplace(term, var));
        }
    }

    public static Conj createConj(Expr a, Expr b){
        return new Conj(a,b);
    }

    public static class Disj extends BinaryOperation {

        public Disj(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public String toString() {
            return "(" + left.toString() + "|" + right.toString() + ")";
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            return pathToFirstFreeEntryImpl(x, Resources.DISJUCNTION);
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return toStringWithReplacedVarImpl(term, var, "|");
        }

        public boolean isFreeToReplace(Term term, String var) {
            return (left.isFreeToReplace(term, var) && right.isFreeToReplace(term, var));
        }
    }

    public static Disj createDisj(Expr a,Expr b){
        return new Disj(a,b);
    }

    public static class Equal extends BinaryOperation {

        public Equal(Term left, Term right) {
            super(left, right);
        }

        public String toString() {
            return "(" + left.toString() + "=" + right.toString() + ")";
        }

        public Expr getLeft() {
            return left;
        }

        public Expr getRight() {
            return right;
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            List<Pair> pathFromCurPos;

            pathFromCurPos = left.pathToFirstFreeEntry(x);
            if (pathFromCurPos != null) {
                ((LinkedList) pathFromCurPos).addFirst(new Pair(Resources.EQUALITY, "left"));
                return pathFromCurPos;
            }

            pathFromCurPos = right.pathToFirstFreeEntry(x);
            if (pathFromCurPos != null) {
                ((LinkedList) pathFromCurPos).addFirst(new Pair(Resources.EQUALITY, "right"));
                return pathFromCurPos;
            }

            return null;
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return "(" + left.toStringWithReplacedVar(term, var) + "="
                    + right.toStringWithReplacedVar(term, var) + ")";
        }

        public boolean isFreeToReplace(Term term, String var) {
            return (left.isFreeToReplace(term, var) && right.isFreeToReplace(term, var));
        }
    }

    public static Equal createEqual(Term a, Term b){
        return new Equal(a,b);
    }

    public static class Impl extends BinaryOperation {
        public Impl(Expr left, Expr right) {
            super(left, right);
        }

        @Override
        public String toString() {
            return "(" + left.toString() + "->" + right.toString() + ")";
        }

        public Expr getLeft() {
            return left;
        }

        public Expr getRight() {
            return right;
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            return pathToFirstFreeEntryImpl(x, Resources.IMPLICATION);
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return toStringWithReplacedVarImpl(term, var, "->");
        }

        public boolean isFreeToReplace(Term term, String var) {
            return (left.isFreeToReplace(term, var) && right.isFreeToReplace(term, var));
        }
    }

    public static Impl createImpl(Expr a, Expr b){
        return new Impl(a,b);
    }

    public static class Neg extends UnaryOperation {
        public Neg(Expr expression) {
            super(expression);
        }

        @Override
        public String toString() {
            return ("!" + expression.toString());
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            List<Pair> resultPath = new ArrayList<Pair>();
            List<Pair> pathFromCurPos = expression.pathToFirstFreeEntry(x);

            if (pathFromCurPos != null) {
                ((LinkedList) pathFromCurPos).addFirst(new Pair(Resources.NEGATION, null));
                return pathFromCurPos;
            }

            return null;
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return "!" + expression.toStringWithReplacedVar(term, var);
        }

        public boolean isFreeToReplace(Term term, String var) {
            return expression.isFreeToReplace(term, var);
        }
    }

    public static Neg createNeg(Expr a){
        return new Neg(a);
    }

}
