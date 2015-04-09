package com.sergeybudkov;

import java.io.*;

public class Main {

    /*
        Task1.
        use:
        input.txt -- insert
        output.txt -- result
     */
    int n, now, largest = 8000;
    String s;
    
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        try {
            solve();
        } catch (IOException ignored) {
        }
    }

    void solve() throws IOException {
        InputStream is = new FileInputStream("input.txt");
        FastScanner in = new FastScanner(is);
        PrintWriter out = new PrintWriter(new File("output.txt"));

        int counter = 1;
        while (in.hasNext()) {
            s = in.next().replace(" ", "");
            n = s.length();
            now = 0;
            if (n == 0) break;
            out.print("(" + counter + ") " + s);
            try {
                Node expr = expr();
                formuls[counter - 1] = expr;
                int axiomNumber  = aksioms(expr);
                if (axiomNumber != -1) {
                    out.println(" (Сх. акс. " + axiomNumber + ")");
                    correct[counter - 1] = true;
                } else {
                    Pair mp = MP(counter - 1);
                    if (mp.first != -1) {
                        out.println(" (M.P. " + (mp.first + 1) + ", " + (mp.second + 1) + ")");
                        correct[counter - 1] = true;
                    } else {
                        out.println(" (Не доказано)");
                    }
                }

            } catch (Exception e) {
                out.println(e.getMessage() + " в " + s);
            }
            counter++;
        }

        out.close();
    }

    int aksioms(Node v) {
        if (aks1(v)) return 1;
        else if (aks2(v)) return 2;
        else if (aks3(v)) return 3;
        else if (aks4(v)) return 4;
        else if (aks5(v)) return 5;
        else if (aks6(v)) return 6;
        else if (aks7(v)) return 7;
        else if (aks8(v)) return 8;
        else if (aks9(v)) return 9;
        else if (aks10(v)) return 10;
        return -1;
    }

    Pair MP(int id) {
        for (int i = id - 1; i >= 0; i--) {
            if (!correct[i]) continue;
            Node AB = formuls[i];
            if (AB != null && AB.s.equals("->") && AB.right != null && formuls[id] != null && checkEqual(AB.right, formuls[id])) {
                for (int j = 0; j < id; j++) {
                    if (!correct[j]) continue;
                    Node A = formuls[j];
                    if (A != null && AB.left != null && checkEqual(A, AB.left)) {
                        return new Pair(j, i);
                    }
                }
            }
        }
        return new Pair(-1, -1);
    }

    Node formuls[] = new Node[largest];
    boolean correct[] = new boolean[largest];



    boolean checkEqualHard(Node a, Node b) {
        return !((a.left != null && b.left == null) || (a.left == null && b.left != null)) && !((a.right != null && b.right == null) || (a.right == null && b.right != null)) && a.s.equals(b.s) && !(a.left != null && b.left != null && !checkEqualHard(a.left, b.left)) && !(a.right != null && b.right != null && !checkEqualHard(a.right, b.right));
    }

    boolean checkEqual(Node a, Node b) {
        return a.hash == b.hash && checkEqualHard(a, b);
    }

    Node expr() throws Exception {
        Node expr1 = disj();
        if (now < n && s.charAt(now) == '-' && s.charAt(++now) == '>') {
            now++;
            Node expr2 = expr();
            return new Node("->", expr1, expr2);
        }
        return expr1;
    }

    Node neg() throws Exception {
        char c = s.charAt(now);
        if (c >= 'A' && c <= 'Z') {
            String name = "";
            name += c;
            now++;
            if (now < n && Character.isDigit(s.charAt(now))) {
                name += s.charAt(now++);
            }
            return new Node(name, null, null);
        } else if (c == '!') {
            now++;
            Node expr = neg();
            return new Node("!", null, expr);
        } else if (c == '(') {
            now++;
            Node expr = expr();
            if (now >= n || s.charAt(now++) != ')') {
                throw new Exception(") не найдена");
            }
            return expr;
        }
        throw new Exception("Неверная формула");
    }

    Node conj() throws Exception {
        Node expr = neg();
        while (now < n && s.charAt(now) == '&') {
            now++;
            Node expr2 = neg();
            expr = new Node("&", expr, expr2);
        }
        return expr;
    }

    Node disj() throws Exception {
        Node expr = conj();
        while (now < n && s.charAt(now) == '|') {
            now++;
            Node expr2 = conj();
            expr = new Node("|", expr, expr2);
        }
        return expr;
    }

    boolean aks1(Node v) {
        return v != null && v.s.equals("->") && v.right != null && v.right.s.equals("->") && (v.left != null && v.right.right != null && checkEqual(v.left, v.right.right));
    }

    boolean aks2(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("->") && v.right != null && v.right.s.equals("->")) {
            if (v.right.left != null && v.right.left.s.equals("->") && v.right.right != null && v.right.right.s.equals("->")) {
                if (v.right.left.right != null && v.right.left.right.s.equals("->")) {
                    if (v.left.left != null && v.left.right != null && v.right.left.left != null && v.right.left.right.left != null && v.right.left.right.right != null &&
                            v.right.right.left != null && v.right.right.right != null) {
                        return ((checkEqual(v.left.left, v.right.left.left) && checkEqual(v.right.right.left, v.left.left)) &&
                                (checkEqual(v.left.right, v.right.left.right.left)) &&
                                (checkEqual(v.right.right.right, v.right.left.right.right)));
                    }
                }
            }
        }
        return false;
    }

    boolean aks3(Node v) {
        if (v != null && v.s.equals("->") && v.right != null && v.right.s.equals("->") && v.right.right != null && v.right.right.s.equals("&")) {
            if (v.left != null && v.right.left != null && v.right.right.left != null && v.right.right.right != null) {
                return (checkEqual(v.left, v.right.right.left) && checkEqual(v.right.left, v.right.right.right));
            }
        }
        return false;
    }

    boolean aks4(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("&")) {
            if (v.left.left != null && v.left.right != null && v.right != null) {
                return checkEqual(v.left.left, v.right);
            }
        }
        return false;
    }

    boolean aks5(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("&")) {
            if (v.left.left != null && v.left.right != null && v.right != null) {
                return checkEqual(v.left.right, v.right);
            }
        }
        return false;
    }

    boolean aks6(Node v) {
        if (v != null && v.s.equals("->") && v.right != null && v.right.s.equals("|")) {
            if (v.left != null && v.right.left != null && v.right.right != null) {
                return checkEqual(v.left, v.right.left);
            }
        }
        return false;
    }

    boolean aks7(Node v) {
        if (v != null && v.s.equals("->") && v.right != null && v.right.s.equals("|")) {
            if (v.left != null && v.right.left != null && v.right.right != null) {
                return checkEqual(v.left, v.right.right);
            }
        }
        return false;
    }

    boolean aks8(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("->") && v.right != null && v.right.s.equals("->")) {
            if (v.right.left != null && v.right.left.s.equals("->") && v.right.right != null && v.right.right.s.equals("->") &&
                    v.right.right.left != null && v.right.right.left.s.equals("|")) {
                if (v.left.left != null && v.left.right != null && v.right.left.left != null && v.right.left.right != null && v.right.right.right != null &&
                        v.right.right.left.left != null && v.right.right.left.right != null) {
                    return (checkEqual(v.left.left, v.right.right.left.left) &&
                            checkEqual(v.right.left.left, v.right.right.left.right) &&
                            checkEqual(v.left.right, v.right.left.right));
                }
            }
        }
        return false;
    }

    boolean aks9(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("->") && v.right != null && v.right.s.equals("->")) {
            if (v.right.left != null && v.right.left.s.equals("->") && v.right.left.right != null && v.right.left.right.s.equals("!") &&
                    v.right.right != null && v.right.right.s.equals("!")) {
                if (v.left.left != null && v.right.left.left != null && v.right.right.right != null && v.left.right != null && v.right.left.right.right != null) {
                    return (checkEqual(v.left.left, v.right.left.left) && checkEqual(v.left.left, v.right.right.right) &&
                            checkEqual(v.left.right, v.right.left.right.right));
                }
            }
        }
        return false;
    }

    boolean aks10(Node v) {
        if (v != null && v.s.equals("->") && v.left != null && v.left.s.equals("!") && v.left.right != null && v.left.right.s.equals("!")) {
            if (v.right != null && v.left.right.right != null) {
                return checkEqual(v.left.right.right, v.right);
            }
        }
        return false;
    }
}