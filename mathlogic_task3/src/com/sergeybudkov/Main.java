package com.sergeybudkov;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

private String checkProof(FastScanner in, PrintWriter out) throws IOException {
        Parser parser = new Parser();
        String curLine = in.next();
        Expr exp = parser.parseExpression(curLine);
        List<String> varNames = parser.getAllVariables(curLine);
        boolean[] cur = new boolean[varNames.size()];
        Map<String, Boolean> failValues = null;
        do {
            Map<String, Boolean> values = new HashMap<String, Boolean>();
            for (int i = 0; i < varNames.size(); i++) {
                values.put(varNames.get(i), cur[i]);
            }
            if (!exp.evaluate(values)) {
                failValues = values;
                break;
            }

        } while (next(cur));
        if (failValues != null) {
            StringBuilder verdict = new StringBuilder("Высказывание ложно при ");
            for (int i = 0; i < varNames.size(); i++) {
                String res;
                if (failValues.get(varNames.get(i)))
                    res = "И";
                else
                    res = "Л";
                verdict.append(varNames.get(i)).append("=").append(res);
                if (i != varNames.size() - 1) {
                    verdict.append(',');
                }
            }
            out.println(verdict.toString());
            return verdict.toString();
        }
        HashMap<String, Boolean> varValues = new HashMap<String, Boolean>();
        for (String name : varNames) {
            varValues.put(name, false);
        }
        Checker.ProofWithAssumptions proof = Resources.getProof(0, exp, varNames, varValues);
        for (Expr step : proof.steps) {
            out.println(step.toString());
        }
        return "proofed";
    }

    private boolean next(boolean[] cur) {
        for (int i = cur.length - 1; i >= 0; i--) {
            if (!cur[i]) {
                cur[i] = true;
                for (int j = i + 1; j < cur.length; j++) {
                    cur[j] = false;
                }
                return true;
            }
        }
        return false;
    }

    PrintWriter out;

    public static void main(String[] args) {
        new Main().run();
    }

    void solve() throws IOException {
        FastScanner in = new FastScanner(new File("input.txt"));
        out = new PrintWriter(new File("output.txt"));
        Main proofer = new Main();
        proofer.checkProof(in, out);
        out.println();
        out.close();
    }

    public void run() {
        try {
            solve();
        } catch (IOException ignored) {
        }
    }

}
