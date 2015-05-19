package com.sergeybudkov;

import java.util.LinkedList;
import java.util.List;

public class Operators {

    public static class Brackets extends UnaryOperator {

        public Brackets(Term term) {
            super(term);
        }

        public String toString() {
            return "(" + term.toString() + ")";
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            List<Pair> pathFromCurPos = term.pathToFirstFreeEntry(x);
            if (pathFromCurPos != null) {
                ((LinkedList) pathFromCurPos).addFirst(new Pair(Resources.BRACKETS, null));
                return pathFromCurPos;
            } else {
                return null;
            }
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return "(" + this.term.toStringWithReplacedVar(term, var) + ")";
        }

        public boolean isFreeToReplace(Term term, String var) {
            return this.term.isFreeToReplace(term, var);
        }
    }

    public static Brackets createBrackets(Term a){
        return  new Brackets(a);
    }

    public static class Incr extends UnaryOperator {

        public Incr(Term term) {
            super(term);
        }

        public String toString() {
            if (term instanceof BinaryOperator) {
                return ("(" + term.toString() + ")\'");
            }
            return (term.toString() + "\'");
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            List<Pair> pathFromCurPos = term.pathToFirstFreeEntry(x);

            if (pathFromCurPos != null) {
                ((LinkedList) pathFromCurPos).addFirst(new Pair(Resources.INCREMENT, null));
                return pathFromCurPos;
            }

            return null;
        }

        public String toStringWithReplacedVar(Term term, String var) {
            if (this.term instanceof BinaryOperator) {
                return ("(" + this.term.toStringWithReplacedVar(term, var) + ")\'");
            }

            if (term instanceof BinaryOperator) {
                if (this.term instanceof Terms.Var && this.term.toString().equals(var)) {
                    return ("(" + this.term.toStringWithReplacedVar(term, var) + ")\'");
                }
            }
            return (this.term.toStringWithReplacedVar(term, var) + "\'");
        }

        public boolean isFreeToReplace(Term term, String var) {
            return this.term.isFreeToReplace(term, var);
        }
    }

    public static Incr createIncr(Term a){
        return new Incr(a);
    }

    public static class Mul extends BinaryOperator {

        public Mul(Term left, Term right) {
            super(left, right);
        }

        public String toString() {
            String res = "";
            if (left instanceof Plus) {
                res += "(" + left.toString() + ")";
            } else {
                res += left.toString();
            }
            res += "*";
            if (right instanceof Plus) {
                res += "(" + right.toString() + ")";
            } else {
                res += right.toString();
            }
            return res;
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            return pathToFirstFreeEntryImpl(x, Resources.MULTIPLY);
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return toStringWithReplacedVarImpl(term, var, "*");
        }

        public boolean isFreeToReplace(Term term, String var) {
            return (left.isFreeToReplace(term, var) && right.isFreeToReplace(term, var));
        }
    }

    public static Mul createMul(Term a, Term b){
        return new Mul(a,b);
    }

    public static class Plus extends BinaryOperator {

        public Plus(Term left, Term right) {
            super(left, right);
        }

        public String toString() {
            return left.toString() + "+" + right.toString();
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            return pathToFirstFreeEntryImpl(x, Resources.PLUS);
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return toStringWithReplacedVarImpl(term, var, "+");
        }

        public boolean isFreeToReplace(Term term, String var) {
            return (left.isFreeToReplace(term, var) && right.isFreeToReplace(term, var));
        }
    }

    public static Plus createPlus(Term a, Term b){
        return new Plus(a,b);
    }

}
