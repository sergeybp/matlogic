package com.sergeybudkov;

import java.util.List;

public class Quantifiers {

    public static class Exist extends AbstractQuantifier {
        public Exist(Expr expression) {
            super(expression);
        }

        public Exist(Expr expression, String connectingVaraible) {
            super(expression);
            var = connectingVaraible;
        }

        @Override
        public String toString() {
            return "?" + var + expression.toString();
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            return pathToFirstFreeEntryImpl(x, Resources.EXIST);
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return toStringWithReplacedVarImpl(term, var, "?");
        }
    }

    public static Exist createExist(Expr a, String b){
        return new Exist(a,b);
    }

    public static class Forall extends AbstractQuantifier {
        public Forall(Expr expression) {
            super(expression);
        }

        public Forall(Expr expression, String connectingVar) {
            super(expression);
            var = connectingVar;
        }

        @Override
        public String toString() {
            return "@" + var + expression.toString();
        }

        public List<Pair> pathToFirstFreeEntry(String x) {
            return pathToFirstFreeEntryImpl(x, Resources.FORALL);
        }

        public String toStringWithReplacedVar(Term term, String var) {
            return toStringWithReplacedVarImpl(term, var, "@");
        }
    }

    public static Forall createForall(Expr a, String b){
        return new Forall(a,b);
    }

}
