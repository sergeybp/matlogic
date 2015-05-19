package com.sergeybudkov;

import java.util.List;

public interface Expr {
    @Override
    public String toString();

    public List<Pair> pathToFirstFreeEntry(String x);

    public String toStringWithReplacedVar(Term term, String var);

    public boolean isFreeToReplace(Term term, String var);
}
