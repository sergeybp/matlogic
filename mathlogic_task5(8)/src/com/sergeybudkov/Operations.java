package com.sergeybudkov;

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.LinkedList;

public class Operations {

    public static class Power extends Ordinal {
        Ordinal left;
        Ordinal right;

        Power(Ordinal left, Ordinal right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return left.toString() + "^" + right.toString();
        }

        CNF toCNF() {
            return left.toCNF().pow(right.toCNF());
        }
    }

    public static Power createPower(Ordinal a,Ordinal b){
        return new Power(a,b);
    }


    public static class Multiply extends Ordinal {
        Ordinal left;
        Ordinal right;

        Multiply(Ordinal left, Ordinal right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return left.toString() + "*" + right;
        }

        CNF toCNF() {
            return left.toCNF().mul(right.toCNF());
        }
    }

    public static Multiply createMultiply(Ordinal a, Ordinal b){
        return new Multiply(a,b);
    }

    public static class Subtract extends Ordinal {
        Ordinal left;
        Ordinal right;

        Subtract(Ordinal left, Ordinal right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return left.toString() + "-" + right;
        }

        CNF toCNF() {
            return left.toCNF().subtract(right.toCNF());
        }
    }

    public static Subtract createSubtract(Ordinal a, Ordinal b){
        return new Subtract(a,b);
    }

    public static class Add extends Ordinal {
        Ordinal left;
        Ordinal right;

        Add(Ordinal left, Ordinal right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return left.toString() + "+" + right;
        }

        CNF toCNF() {
            return left.toCNF().plus(right.toCNF());
        }
    }

    public static Add createAdd(Ordinal a,Ordinal b){
        return new Add(a,b);
    }

    public static class W extends Ordinal {
        @Override
        public String toString() {
            return "w";
        }

        CNF toCNF() {
            try {
                LinkedList<Pair<CNF, BigInteger>> list = new LinkedList<Pair<CNF, BigInteger>>();
                list.add(new Pair<CNF, BigInteger>(new Atom(BigInteger.ONE), BigInteger.ONE));
                return new OrdinalList(list, new Atom(BigInteger.ZERO));
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static W createW(){
        return new W();
    }

    public static class Number extends Ordinal {
        Integer number;

        Number(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return number.toString();
        }

        CNF toCNF() {
            try {
                return new Atom(BigInteger.valueOf(number));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Number createNumber(Integer a){
        return new Number(a);
    }
}
