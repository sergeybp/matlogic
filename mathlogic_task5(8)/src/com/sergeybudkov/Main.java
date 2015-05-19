package com.sergeybudkov;

import java.io.*;
import java.math.BigInteger;

public class Main {

    void solve() throws IOException {
        InputStream is = new FileInputStream("input.in");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("output.out"));
        String s = in.readLine();
        while (in.hasNext())
            s += in.readLine();
        String first = s.substring(0, s.indexOf("="));
        String second = s.substring(s.indexOf("=") + 1, s.length());
        try {
            Ordinal arg1 = Parser.parse(first);
            Ordinal arg2 = Parser.parse(second);
            String result1 = arg1.toCNF().toString();
            String result2 = arg2.toCNF().toString();
            if (result1.equals(result2))
                out.println("Равны");
            else out.println("Не равны");
        } catch (Exception e) {
            out.println("Check your input file");
        }
        out.close();
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        try {
            solve();
        } catch (IOException ignored) {
        }
    }



}