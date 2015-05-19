package com.sergeybudkov;

import java.util.LinkedList;
import java.util.List;

public class Predicate implements Expr {
    private String value;
    private List<Term> subTerms;
    public Predicate(String value) {
        this.value = value;
    }

    public Predicate(String value, List<Term> subTerms) {
        this.value = value;
        this.subTerms = subTerms;
    }

    public String getValue() {
        return value;
    }

    public List<Term> getSubTerms() {
        return subTerms;
    }

    public String toString() {
        if (subTerms == null || subTerms.size() == 0) {
            return value;
        } else {
            StringBuilder res = new StringBuilder();
            res.append(value).append("(");
            res.append(subTerms.get(0).toString());
            for (int i = 1; i < subTerms.size(); i++) {
                res.append(",");
                res.append(subTerms.get(i).toString());
            }
            res.append(")");
            return res.toString();
        }
    }

    public List<Pair> pathToFirstFreeEntry(String x) {
        if (subTerms == null) {
            return null;
        }

        for (int i = 0; i < subTerms.size(); i++) {
            List<Pair> pathFromCurPos = subTerms.get(i).pathToFirstFreeEntry(x);
            if (pathFromCurPos != null) {
                ((LinkedList) pathFromCurPos).addFirst(new Pair(Resources.PREDICATE, new Pair(value, i)));
                return pathFromCurPos;
            }
        }

        return null;
    }

    public String toStringWithReplacedVar(Term term, String var) {
        if (subTerms == null || subTerms.size() == 0) {
            return value;
        } else {
            StringBuilder res = new StringBuilder();
            res.append(value).append("(");
            res.append(subTerms.get(0).toStringWithReplacedVar(term, var));
            for (int i = 1; i < subTerms.size(); i++) {
                res.append(",");
                res.append(subTerms.get(i).toStringWithReplacedVar(term, var));
            }
            res.append(")");
            return res.toString();
        }
    }

    public boolean isFreeToReplace(Term term, String var) {
        return true;
    }
}
