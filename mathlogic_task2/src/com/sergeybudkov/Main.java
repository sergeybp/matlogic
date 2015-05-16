package com.sergeybudkov;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    FastScanner in;
    PrintWriter out;


    public static void main(String[] arg) {
        new Main().run();
    }

    public void run() {
        try {
            InputStream is = new FileInputStream("input.txt");
            in = new FastScanner(is);
            out = new PrintWriter(new File("output.txt"));
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void solve() throws IOException {
        ArrayList<Expr> expressions = new ArrayList<Expr>();
        String s = in.next();
        Parser parser;
        String[] argument = s.split(",");
        ArrayList<Expr> added = new ArrayList<Expr>();
        for (int i = 0; i < argument.length - 1; i++) {
            parser = new Parser(argument[i]);
            added.add(parser.parse());
        }
        String[] argTask = argument[argument.length - 1].split("\\|-");
        Expr alpha = (new Parser(argTask[0])).parse();
        ArrayList<Expr> answer = new ArrayList<Expr>();
        if (in.hasNext())
            s = in.next();

        while (in.hasNext()) {
            parser = new Parser(s);
            expressions.add(parser.parse());
            s = in.next();
        }
        boolean isgood = true;
        int i = 0;
        for (Expr expr : expressions) {
            i++;
            isgood = false;
            if (Axioms.checkAxioms(expr) != -1) {
                answer.add(expr);
                answer.add(new Impl(expr, new Impl(alpha, expr)));
                answer.add(new Impl(alpha, expr));
                isgood = true;
                continue;
            }
            for (Expr g : added) {
                if (g.equals(expr)) {
                    answer.add(expr);
                    answer.add(new Impl(expr, new Impl(alpha, expr)));
                    answer.add(new Impl(alpha, expr));
                    isgood = true;

                }
                if (isgood) break;
            }
            if (alpha.equals(expr)) {
                answer.add(new Impl(alpha, new Impl(alpha, alpha)));
                answer.add(new Impl(new Impl(alpha, new Impl(alpha, alpha)),
                        new Impl(new Impl(alpha, new Impl(
                                new Impl(alpha, alpha), alpha)), new Impl(alpha, alpha))
                ));
                answer.add(new Impl(new Impl(alpha, new Impl(new Impl(alpha, alpha), alpha)),
                        new Impl(alpha, alpha)));
                answer.add(new Impl(alpha, new Impl(new Impl(alpha, alpha), alpha)));
                answer.add(new Impl(alpha, alpha));
                isgood = true;
                continue;
            }

            for (int j = 0; j < i - 1; j++) {
                Expr mp = expressions.get(j);
                if (mp instanceof Impl) {
                    Impl impl = (Impl) mp;
                    if (impl.getRight().equals(expr)) {
                        for (int k = 0; k < i - 1; k++) {
                            Expr mp2 = expressions.get(k);
                            if (mp2.equals(impl.getLeft())) {
                                answer.add(new Impl(new Impl(alpha, mp2), new Impl(new
                                        Impl(alpha, new Impl(mp2, expr)), new Impl(alpha, expr))));
                                answer.add(new Impl(new Impl(alpha, new Impl(mp2, expr)),
                                        new Impl(alpha, expr)));
                                answer.add(new Impl(alpha, expr));
                                isgood = true;
                                break;
                            }
                        }
                    }
                    if (isgood)
                        break;
                }
            }
            if (!isgood) {
                break;
            }
        }
        if (isgood) {
            for (Expr expr : answer) {
                out.println(expr.toString());
            }
        } else {
            for (Expr expr : answer) {
                out.println(expr.toString());
            }
            out.println("Error");
        }
    }




}
