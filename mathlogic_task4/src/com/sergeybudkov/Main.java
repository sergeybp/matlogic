package com.sergeybudkov;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    FastScanner in;
    PrintWriter out;
    List<Expr> inputProof = new ArrayList<>();
    List<String> outputProof = new ArrayList<>();
    List<String> forallProof = new ArrayList<>();
    List<String> existProof = new ArrayList<>();
    String[] conditions;
    Expr alphaExp;
    String alpha;
    String beta;
    Resources resources;

    private static final String RES_PATH = "";
    private FastScanner inProof;
    private void loadForallProof() throws MyException {
        for(int i = 0 ; i < 83;i++) {
            forallProof.add(resources.forall_res[i]);
        }
    }

    private void loadExistProof() throws MyException {
        for(int i = 0 ; i < 107;i++) {
            existProof.add(resources.exist_res[i]);
        }
    }

    private void initConditions(String headString) throws MyException {
        String[] y = headString.split("\\|-");
        String onlyFirstPart = y[0];
        String[] x = onlyFirstPart.split(",");
        conditions = new String[x.length - 1];
        for (int i = 0; i < (x.length - 1); i++) {
            conditions[i] = parse(x[i]);
        }


        alphaExp = (new Parser()).parse(x[x.length - 1]);
        alpha = parse(x[x.length - 1]);
        beta = parse(y[1]);
    }

    private boolean isCondition(String curLine) {
        for (String condition : conditions) {
            if (curLine.equals(condition)) {
                return true;
            }
        }
        return false;
    }

    private void solve() throws IOException, MyException {
        StringBuilder incorrectInputMsg = new StringBuilder();
        resources = new Resources();
        resources.init();
        loadForallProof();
        loadExistProof();
        String inputString;
        if ((inputString = in.nextLine()) != null) {
            initConditions(inputString);
        }
        while ((inputString = in.nextLine()) != null) {
            inputProof.add((new Parser()).parse(inputString));
        }
        StringBuilder headString = new StringBuilder();
        if (conditions.length > 0) {
            headString.append(conditions[0]);
        }
        for (int i = 1; i < conditions.length; i++) {
            headString.append(",").append(conditions[i]);
        }
        headString.append("|-(").append(alpha).append("->").append(beta).append(")");
        Expr curExp;
        String curLine;
        String tmp;
        for (int i = 0; i < inputProof.size(); i++) {
            curExp = inputProof.get(i);
            curLine = curExp.toString();
            if ((Axioms.doesMatchAxioms(curExp)) || ((conditions.length > 0)
                    && isCondition(curLine))) {
                tmp = curLine + "->" + alpha + "->" + curLine;
                outputProof.add(tmp);
                tmp = curLine;
                outputProof.add(tmp);
                tmp = alpha + "->" + curLine;
                outputProof.add(tmp);

                continue;
            }
            if (Axioms.getFlag()) {
                incorrectInputMsg.append(Resources.INCORRECT_INPUT);
                incorrectInputMsg.append(i + 1);
                incorrectInputMsg.append(": ").append(Axioms.getErrorMsg());
                break;
            }
            if (curLine.equals(alpha)) {
                tmp = alpha + "->((" + alpha + "->" + alpha + ")->" + alpha + ")";
                outputProof.add(tmp);
                tmp = alpha + "->(" + alpha + "->" + alpha + ")";
                outputProof.add(tmp);
                tmp = "(" + alpha + "->(" + alpha + "->" + alpha + "))->" +
                        "(" + alpha + "->((" + alpha + "->" + alpha + ")->" + alpha + "))->" +
                        "(" + alpha + "->" + alpha + ")";
                outputProof.add(tmp);
                tmp = "(" + alpha + "->((" + alpha + "->" + alpha + ")->" + alpha + "))->" +
                        "(" + alpha + "->" + alpha + ")";
                outputProof.add(tmp);
                tmp =  "(" + alpha + "->" + alpha + ")";
                outputProof.add(tmp);
                continue;
            }
            String lineJ = isModusPonensOfTwoLines(curLine, i);
            if (lineJ != null) {
                tmp = "(" + alpha + "->" + lineJ + ")->" +
                        "(" + alpha + "->" + lineJ + "->" + curLine + ")->" +
                        "(" + alpha + "->" + curLine + ")";
                outputProof.add(tmp);
                tmp = "(" + alpha + "->" + lineJ + "->" + curLine + ")->" +
                        "(" + alpha + "->" + curLine + ")";
                outputProof.add(tmp);
                tmp = "(" + alpha + "->" + curLine + ")";
                outputProof.add(tmp);
                continue;
            }
            lineJ = isApplicationOfForallRule(curExp);
            if (lineJ == null) {
                String left = ((Operations.Impl) curExp).getLeft().toString();
                String right = ((Quantifiers.Forall) ((Operations.Impl) curExp).getRight()).getExpression().toString();
                String var = ((Quantifiers.Forall) ((Operations.Impl) curExp).getRight()).getVar();
                insertQuantifierProof(forallProof, left, right, var);
                continue;
            } else if (!lineJ.equals("NOT OK")) {
                incorrectInputMsg.append(Resources.INCORRECT_INPUT);
                incorrectInputMsg.append(i + 1);
                incorrectInputMsg.append(": ").append(lineJ);
                break;
            }
            lineJ = isApplicationOfExistRule(curExp);
            if (lineJ == null) {
                String left = ((Quantifiers.Exist) ((Operations.Impl) curExp).getLeft()).getExpression().toString();
                String right = ((Operations.Impl) curExp).getRight().toString();
                String var = ((Quantifiers.Exist) ((Operations.Impl) curExp).getLeft()).getVar();
                insertQuantifierProof(existProof, left, right, var);
                continue;
            } else if (!lineJ.equals("NOT OK")) {
                incorrectInputMsg.append(Resources.INCORRECT_INPUT);
                incorrectInputMsg.append(i + 1);
                incorrectInputMsg.append(": ").append(lineJ);
                break;
            }

            incorrectInputMsg.append(Resources.INCORRECT_INPUT);
            incorrectInputMsg.append(i + 1);
            break;
        }

        if (incorrectInputMsg.length() > 0) {
            out.println(incorrectInputMsg);
        } else {
            out.println(headString);
            for (String anOutputProof : outputProof) {
                out.println(parse(anOutputProof));
            }
        }
    }

    private String isModusPonensOfTwoLines(String curLine, int k) {
        String lineJ;
        String requiredString;
        for (int i = 0; i < k; i++) {
            lineJ = inputProof.get(i).toString();
            requiredString = "(" + lineJ + "->" + curLine + ")";
            for (int j = 0; j < k; j++) {
                if (requiredString.equals(inputProof.get(j).toString())) {
                    return lineJ;
                }
            }
        }

        return null;
    }

    private static final String A = "A77";
    private static final String B = "B77";
    private static final String C = "C77";
    private static final String VAR = "v77";
    private void insertQuantifierProof(List<String> proof, String left, String right, String var) {
        String s;
        for (String aProof : proof) {
            s = aProof;
            s = s.replace(A, alpha);
            s = s.replace(B, left);
            s = s.replace(C, right);
            s = s.replace(VAR, var);
            outputProof.add(s);
        }
    }

    private String isApplicationOfForallRule(Expr curExp) throws MyException {
        String var;
        if (curExp instanceof Operations.Impl) {
            Expr right = ((Operations.Impl) curExp).getRight();
            if (right instanceof Quantifiers.Forall) {
                var = ((Quantifiers.Forall) right).getVar();
            } else {
                return "NOT OK";
            }
        } else {
            return "NOT OK";
        }

        String curLine = curExp.toString();
        String requiredString;
        Expr expJ;

        for (Expr anInputProof : inputProof) {
            expJ = anInputProof;
            if (expJ instanceof Operations.Impl) {
                requiredString = parse("(" + ((Operations.Impl) expJ).getLeft().toString() + "->@" +
                        var + ((Operations.Impl) expJ).getRight().toString() + ")");
                if (requiredString.equals(curLine)) {
                    return isVarFreeInExpression(((Operations.Impl) curExp).getLeft(), var);
                }
            }
        }
        return "NOT OK";
    }

    private String isApplicationOfExistRule(Expr curExp) throws MyException {
        String var;
        if (curExp instanceof Operations.Impl) {
            Expr left = ((Operations.Impl) curExp).getLeft();
            if (left instanceof Quantifiers.Exist) {
                var = ((Quantifiers.Exist) left).getVar();
            } else {
                return "NOT OK";
            }
        } else {
            return "NOT OK";
        }

        String curLine = curExp.toString();
        String requiredString;
        Expr expJ;

        for (Expr anInputProof : inputProof) {
            expJ = anInputProof;
            if (expJ instanceof Operations.Impl) {
                requiredString = parse("(?" + var + ((Operations.Impl) expJ).getLeft().toString() +
                        "->" + ((Operations.Impl) expJ).getRight().toString() + ")");
                if (requiredString.equals(curLine)) {
                    return isVarFreeInExpression(((Operations.Impl) curExp).getRight(), var);
                }
            }
        }
        return "NOT OK";
    }

    private String isVarFreeInExpression(Expr exp, String var) {
        if (exp.pathToFirstFreeEntry(var) != null) {
            return ("используется правило с квантором по" +
                    " переменной " + var + " входящей" +
                    " свободно в допущение " + exp.toString());
        }


        if (alphaExp.pathToFirstFreeEntry(var) != null) {
            return ("используется правило с квантором по" +
                    " переменной " + var + " входящей" +
                    " свободно в допущение " + alpha);
        }

        return null;
    }

    private String parse(String curLine) throws MyException {
        return (new Parser()).parse(curLine).toString();
    }

    public void run() {
        try {
            in = new FastScanner(new File("input.txt"));
            out = new PrintWriter(new File("output.txt"));
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException me) {
            System.out.println(me.getMessage());
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
