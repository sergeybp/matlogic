package com.sergeybudkov;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Axioms {
    private static Pattern[] statementsAxioms = {
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}(.+)->\\1[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)->(.+)[\\)]{1}->[\\(]{2}\\1->" +
                            "[\\(]{1}\\2->(.+)[\\)]{2}->[\\(]{1}\\1->\\3[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}(.+)->[\\(]{1}\\1&\\2[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)&(.+)[\\)]{1}->\\1[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)&(.+)[\\)]{1}->\\2[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}\\1\\|(.+)[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)->[\\(]{1}(.+)\\|\\1[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)->(.+)[\\)]{1}->[\\(]{2}(.+)" +
                            "->\\2[\\)]{1}->[\\(]{2}\\1\\|\\3[\\)]{1}->\\2[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)->(.+)[\\)]{1}->[\\(]{2}\\1->!\\2[\\)]{1}->!\\1[\\)]{2}$"),
            Pattern.compile("^[\\(]{1}!!(.+)->\\1[\\)]{1}$")
    };


    private static Pattern[] arithmeticAxioms = {
            Pattern.compile(
                    "^[\\(]{2}(.+)=(.+)[\\)]{1}->[\\(]{1}\\1'=\\2'[\\)]{2}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)=(.+)[\\)]{1}->[\\(]{2}\\1=(.+)[\\)]{1}->[\\(]{1}" +
                            "\\2=\\3[\\)]{3}$"),
            Pattern.compile(
                    "^[\\(]{2}(.+)'=(.+)'[\\)]{1}->[\\(]{1}\\1=\\2[\\)]{2}$"),
            Pattern.compile(
                    "^![\\(]{1}(.+)'=0[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\+(.+)'=[\\(]{1}\\1\\+\\2[\\)]{1}'[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\+0=\\1[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\*0=0[\\)]{1}$"),
            Pattern.compile(
                    "^[\\(]{1}(.+)\\*(.+)'=\\1\\*\\2\\+\\1[\\)]{1}$")


    };


    private static boolean flag = false;
    private static String errorMsg = "";


    public static boolean doesMatchAxioms(Expr exp) {

        flag = false;
        errorMsg = "";

        String expAsString = exp.toString();
        Matcher matcher;
        for (Pattern statementsAxiom : statementsAxioms) {
            matcher = statementsAxiom.matcher(expAsString);
            if (matcher.matches()) {
                return true;
            }
        }

        return doesMatchForallAxiom(exp) || !flag && doesMatchExistAxiom(exp) || doesMatchArithmeticsAxioms(exp);


    }

    private static boolean doesMatchForallAxiom(Expr exp) {
        if (exp instanceof Operations.Impl) {
            if (((Operations.Impl) exp).left instanceof Quantifiers.Forall) {
                String var = (((Quantifiers.Forall) ((Operations.Impl) exp).left).var);
                Expr phi = (((Quantifiers.Forall) ((Operations.Impl) exp).left).expression);
                Expr newPhi = ((Operations.Impl) exp).right;
                return areTwoExpEqualAfterSubst(phi, newPhi, var);
            }

        }
        return false;
    }

    private static boolean doesMatchExistAxiom(Expr exp) {
        if (exp instanceof Operations.Impl) {
            if (((Operations.Impl) exp).right instanceof Quantifiers.Exist) {
                String var = (((Quantifiers.Exist) ((Operations.Impl) exp).right).var);
                Expr phi = (((Quantifiers.Exist) ((Operations.Impl) exp).right).expression);
                Expr newPhi = ((Operations.Impl) exp).left;
                return areTwoExpEqualAfterSubst(phi, newPhi, var);
            }
        }
        return false;
    }

    private static boolean doesMatchArithmeticsAxioms(Expr exp) {
        Matcher matcher;
        String expAsString = exp.toString();
        Expr tmp;

        Expr left;
        Expr right;
        if (exp instanceof Operations.Impl) {
            if (((Operations.Impl) exp).left instanceof Operations.Equal &&
                    ((Operations.Impl) exp).right instanceof Operations.Equal) {

                matcher = arithmeticAxioms[0].matcher(expAsString);
                if (matcher.matches()) {
                    return true;
                }

                left = ((Operations.Impl) exp).left;
                right = ((Operations.Impl) exp).right;
                if (((Operations.Equal) right).left instanceof Operators.Incr &&
                        ((Operations.Equal) right).right instanceof Operators.Incr) {
                    if (((Operations.Equal) left).left.toString().equals(
                            ((Operators.Incr) ((Operations.Equal) right).left).term.toString())
                            &&
                            ((Operations.Equal) left).right.toString().equals(
                                    ((Operators.Incr) ((Operations.Equal) right).right).term.toString())) {
                        return true;
                    }
                }
            }

            if (((Operations.Impl) exp).left instanceof Operations.Equal &&
                    ((Operations.Impl) exp).right instanceof Operations.Impl) {
                tmp = ((Operations.Impl) exp).right;
                if (((Operations.Impl) tmp).left instanceof Operations.Equal &&
                        ((Operations.Impl) tmp).right instanceof Operations.Equal) {
                    matcher = arithmeticAxioms[1].matcher(expAsString);
                    if (matcher.matches()) {
                        return true;
                    }
                }

            }

            if (((Operations.Impl) exp).left instanceof Operations.Equal &&
                    ((Operations.Impl) exp).right instanceof Operations.Equal) {
                matcher = arithmeticAxioms[2].matcher(expAsString);
                if (matcher.matches()) {
                    return true;
                }

                left = ((Operations.Impl) exp).left;
                right = ((Operations.Impl) exp).right;
                if (((Operations.Equal) left).left instanceof Operators.Incr &&
                        ((Operations.Equal) left).right instanceof Operators.Incr) {
                    if (((Operations.Equal) right).left.toString().equals(
                            ((Operators.Incr) ((Operations.Equal) left).left).term.toString())
                            &&
                            ((Operations.Equal) right).right.toString().equals(
                                    ((Operators.Incr) ((Operations.Equal) left).right).term.toString())) {
                        return true;
                    }
                }

            }
        }

        if (exp instanceof Operations.Neg &&
                ((Operations.Neg) exp).expression instanceof Operations.Equal) {

            matcher = arithmeticAxioms[3].matcher(expAsString);
            if (matcher.matches()) {
                return true;
            }
        }

        if (exp instanceof Operations.Equal) {
            for (int i = 4; i < 8; i++) {
                matcher = arithmeticAxioms[i].matcher(expAsString);
                if (matcher.matches()) {
                    return true;
                }
            }


            if (((Operations.Equal) exp).left instanceof Operators.Plus &&
                    ((Operations.Equal) exp).right instanceof Operators.Incr) {
                left = ((Operations.Equal) exp).left;
                right = ((Operations.Equal) exp).right;
                if (((Operators.Incr) right).term instanceof Operators.Plus &&
                        ((Operators.Plus) left).right instanceof Operators.Incr) {
                    // if (a1 == a2 && b1 == b2)
                    if (((Operators.Plus) left).left.toString().equals(
                            ((Operators.Plus) ((Operators.Incr) right).term).left.toString())
                            &&
                            ((Operators.Incr) ((Operators.Plus) left).right).term.toString().equals(
                                    ((Operators.Plus) ((Operators.Incr) right).term).right.toString())) {
                        return true;
                    }
                }
            }

            if (((Operations.Equal) exp).left instanceof Operators.Mul &&
                    ((Operations.Equal) exp).right instanceof Operators.Plus) {

                left = ((Operations.Equal) exp).left;
                right = ((Operations.Equal) exp).right;
                if (((Operators.Mul) left).right instanceof Operators.Incr &&
                        ((Operators.Plus) right).left instanceof Operators.Mul) {
                    if (((Operators.Mul) left).left.toString().equals(
                            ((Operators.Plus) right).right.toString())
                            &&
                            ((Operators.Mul) left).left.toString().equals(
                                    ((Operators.Mul) ((Operators.Plus) right).left).left.toString())
                            &&
                            ((Operators.Incr) ((Operators.Mul) left).right).term.toString().equals(
                                    ((Operators.Mul) ((Operators.Plus) right).left).right.toString())) {

                        return true;
                    }
                }
            }
        }

        Expr phi;
        Expr phiWithZero;
        Expr phiWithInc;
        String x;
        if (exp instanceof Operations.Impl) {
            phi = ((Operations.Impl) exp).right;
            if (((Operations.Impl) exp).left instanceof Operations.Conj) {
                tmp = ((Operations.Conj) (((Operations.Impl) exp).left)).right;
                if (tmp instanceof Quantifiers.Forall) {
                    x = ((Quantifiers.Forall) tmp).getVar();
                    phiWithZero = ((Operations.Conj) (((Operations.Impl) exp).left)).left;
                    String tmpString = phi.toStringWithReplacedVar(Terms.createZero(), x);
                    if (tmpString.equals(phiWithZero.toString())) {
                        tmp = ((Quantifiers.Forall) tmp).getExpression();
                        if (tmp instanceof Operations.Impl) {
                            phiWithInc = ((Operations.Impl) tmp).getRight();
                            tmpString = phi.toStringWithReplacedVar(new Operators.Incr(new Terms.Var(x)), x);
                            if (tmpString.equals(phiWithInc.toString())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private static boolean areTwoExpEqualAfterSubst(Expr phi, Expr newPhi, String var) {
        List<Pair> pathToFirstFreeEntry = phi.pathToFirstFreeEntry(var);
        if (pathToFirstFreeEntry == null) {
            return (newPhi.toString().equals(phi.toString()));
        }
        Term replacingTerm = getReplacingTerm(pathToFirstFreeEntry, newPhi);
        if (replacingTerm != null) {
            String expWithReplacedVar = phi.toStringWithReplacedVar(replacingTerm, var);
            if (expWithReplacedVar.equals(newPhi.toString())) {
                if (phi.isFreeToReplace(replacingTerm, var)) {
                    return true;
                } else {
                    flag = true;
                    errorMsg = ("терм " + replacingTerm.toString() +
                            " не свободен для подстановки в формулу " +
                            phi.toString() + " вместо переменной " + var);
                }
            }
        }
        return false;
    }

    private static Term getReplacingTerm(List<Pair> path, Expr newPhi) {
        Expr exp = newPhi;
        for (Pair expPair : path) {
            switch (expPair.expressionType) {
                case Resources.IMPLICATION:
                    if (exp instanceof Operations.Impl) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Operations.Impl) exp).left;
                        } else {
                            exp = ((Operations.Impl) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Resources.CONJUCNTION:
                    if (exp instanceof Operations.Conj) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Operations.Conj) exp).left;
                        } else {
                            exp = ((Operations.Conj) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Resources.DISJUCNTION:
                    if (exp instanceof Operations.Disj) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Operations.Disj) exp).left;
                        } else {
                            exp = ((Operations.Disj) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Resources.EQUALITY:
                    if (exp instanceof Operations.Equal) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Operations.Equal) exp).left;
                        } else {
                            exp = ((Operations.Equal) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Resources.NEGATION:
                    if (exp instanceof Operations.Neg) {
                        exp = ((Operations.Neg) exp).expression;
                        continue;
                    } else {
                        return null;
                    }
                case Resources.FORALL:
                    if (exp instanceof Quantifiers.Forall &&
                            ((Quantifiers.Forall) exp).var.equals(expPair.additionalInfo)) {

                        exp = ((Quantifiers.Forall) exp).expression;
                        continue;
                    } else {
                        return null;
                    }
                case Resources.EXIST:
                    if (exp instanceof Quantifiers.Exist &&
                            ((Quantifiers.Exist) exp).var.equals(expPair.additionalInfo)) {

                        exp = ((Quantifiers.Exist) exp).expression;
                        continue;
                    } else {
                        return null;
                    }
                case Resources.PREDICATE:
                    if (exp instanceof Predicate) {
                        Pair pair = (Pair) expPair.additionalInfo;
                        List<Term> subTerms = ((Predicate) exp).getSubTerms();

                        if (pair.expressionType.equals(((Predicate) exp).getValue()) &&
                                subTerms.size() > ((Integer) pair.additionalInfo)) {

                            exp = subTerms.get(((Integer) pair.additionalInfo));
                            continue;
                        }
                    }
                    return null;
                case Resources.PLUS:
                    if (exp instanceof Operators.Plus) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Operators.Plus) exp).left;
                        } else {
                            exp = ((Operators.Plus) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Resources.MULTIPLY:
                    if (exp instanceof Operators.Mul) {
                        if ("left".equals(expPair.additionalInfo)) {
                            exp = ((Operators.Mul) exp).left;
                        } else {
                            exp = ((Operators.Mul) exp).right;
                        }
                        continue;
                    } else {
                        return null;
                    }
                case Resources.INCREMENT:
                    if (exp instanceof Operators.Incr) {
                        exp = ((Operators.Incr) exp).term;
                        continue;
                    } else {
                        return null;
                    }
                case Resources.TERMWITHARGS:
                    if (exp instanceof Terms.TermWithArgs) {

                        Pair pair = (Pair) expPair.additionalInfo;
                        String value = ((Terms.TermWithArgs) exp).getValue();
                        List<Term> subTerms = ((Terms.TermWithArgs) exp).getSubTerms();
                        if (value.equals(pair.expressionType) &&
                                subTerms.size() > ((Integer) pair.additionalInfo)) {
                            exp = subTerms.get(((Integer) pair.additionalInfo));
                            continue;
                        }
                    } else {
                        return null;
                    }
                case Resources.BRACKETS:
                    if (exp instanceof Operators.Brackets) {
                        exp = ((Operators.Brackets) exp).term;
                        continue;
                    } else {
                        return null;
                    }
                case Resources.VARIABLE:
                    if (exp instanceof Term) {
                        return (Term) exp;
                    } else {
                        return null;
                    }
            }
        }
        return null;
    }

    public static boolean getFlag() {
        return flag;
    }

    public static String getErrorMsg() {
        return errorMsg;
    }
}
