package com.sergeybudkov;

import java.lang.*;

public class Parser {
    public static String input;
    public static Character nowToken;
    public static StringBuilder number;

    public static boolean flag = false;
    private static int nowIndex;

    public static void getChar() throws Exception {
        if (nowIndex > input.length()) {
            throw new Exception("parse fail");
        }
        if (nowIndex >= input.length()) {
            nowIndex++;
            return;
        }
        while (nowIndex < input.length() && Character.isWhitespace(input.charAt(nowIndex))) {
            nowIndex++;
        }
        number = new StringBuilder();
        boolean flag = false;
        while (nowIndex < input.length() && (Character.isDigit(input.charAt(nowIndex)))) {
            nowToken = input.charAt(nowIndex);
            number.append(String.valueOf(input.charAt(nowIndex)));
            nowIndex++;
            flag = true;
        }
        if (flag) return;
        if (nowIndex >= input.length()) return;
        if (!(Character.isDigit(input.charAt(nowIndex)))) {
            char tmp = input.charAt(nowIndex);
            nowIndex++;
            nowToken = tmp;
        }
    }

    public static Ordinal parse(String s) throws Exception {
        input = s;
        Ordinal v = Operations.createNumber(0);
        nowIndex = 0;
        if (!input.isEmpty()) {
            getChar();
            v = expression(false);
        }
        if (nowIndex - 1 != input.length())
            throw new Exception("parse fail");

        return v;
    }

    public static Ordinal primary(boolean get) throws Exception {
        if (get) {
            getChar();
        }
        if (Character.isDigit(nowToken)) {
            int tmp1;
            if (number.toString().equals(String.valueOf(Integer.MIN_VALUE).substring(1))) {
                flag = true;
            }
            try {
                if (flag) {
                    number = new StringBuilder();
                    number.append(Integer.MIN_VALUE);
                }
                tmp1 = Integer.parseInt(number.toString());
            } catch (Exception e) {
                throw new Exception("parse fail");
            }

            Ordinal tmp = Operations.createNumber(tmp1);
            getChar();
            if (nowIndex <= input.length() && (nowToken == 'w' || Character.isDigit(nowToken)))
                throw new Exception("parse fail");
            return tmp;
        } else if (nowToken == '-') {
            flag = false;
            Ordinal tmp = primary(true);
            if (!flag)
                return Operations.createSubtract(Operations.createNumber(0), tmp);
            else {
                flag = false;
                return tmp;
            }
        } else if (nowToken == '(') {
            Ordinal tmp = expression(true);
            if (nowToken != ')') {
                throw new Exception("parse fail");
            }
            getChar();
            return tmp;
        } else if (nowToken == ')') {
            throw new Exception("parse fail");
        } else if (nowToken == 'w') {
            Ordinal tmp = Operations.createW();
            getChar();
            if (nowIndex <= input.length() && (Character.isDigit(nowToken) || nowToken == 'w'))
                throw new Exception("parse fail");
            return tmp;
        } else
            throw new Exception("parse fail");

    }

    public static Ordinal subTerminal(boolean get) throws Exception {
        Ordinal left = primary(get);
        while (nowIndex < input.length()) {
            switch (nowToken) {
                case '^': {
                    Ordinal tmp = primary(true);
                    left = Operations.createPower(left, tmp);
                    break;
                }

                default:
                    return left;
            }

        }

        return left;
    }

    public static Ordinal terminal(boolean get) throws Exception {
        Ordinal left = subTerminal(get);

        while (nowIndex < input.length()) {
            switch (nowToken) {
                case '*': {
                    Ordinal tmp = subTerminal(true);
                    left = Operations.createMultiply(left, tmp);
                    break;
                }

                default:
                    return left;
            }

        }

        return left;
    }

    public static Ordinal expression(boolean get) throws Exception {
        Ordinal left = terminal(get);
        while (nowIndex < input.length()) {
            switch (nowToken) {
                case '+': {
                    left = Operations.createAdd(left, terminal(true));
                    break;
                }
                case '-': {
                    left = Operations.createSubtract(left, terminal(true));
                    break;
                }
                default:
                    return left;
            }
        }

        return left;
    }

}
