package com.sergeybudkov;

public class Axioms {

    private static boolean aks1(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getRight() instanceof Impl) {
                Impl impl2 = (Impl) impl1.getRight();
                return (impl1.getLeft().equals(impl2.getRight()));
            }
        }
        return false;
    }

    private static boolean aks2(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getLeft() instanceof Impl) {
                Impl impl2 = (Impl) impl1.getLeft();
                if (impl1.getRight() instanceof Impl) {
                    Impl impl3 = (Impl) impl1.getRight();
                    if (impl3.getLeft() instanceof Impl) {
                        Impl impl4 = (Impl) impl3.getLeft();
                        if (impl4.getRight() instanceof Impl) {
                            Impl impl5 = (Impl) impl4.getRight();
                            if (impl3.getRight() instanceof Impl) {
                                Impl impl6 = (Impl) impl3.getRight();
                                boolean fl1 = impl2.getLeft().equals(impl4.getLeft()) && impl2.getLeft().equals(impl6.getLeft());
                                boolean fl2 = impl2.getRight().equals(impl5.getLeft());
                                boolean fl3 = impl5.getRight().equals(impl6.getRight());
                                return (fl1 && fl2 && fl3);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean aks3(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getRight() instanceof Impl) {
                Impl impl2 = (Impl) impl1.getRight();
                if (impl2.getRight() instanceof Conj) {
                    Conj conj1 = (Conj) impl2.getRight();
                    boolean fl1 = impl1.getLeft().equals(conj1.getLeft());
                    boolean fl2 = impl2.getLeft().equals(conj1.getRight());
                    return (fl1 && fl2);
                }
            }
        }
        return false;
    }

    private static boolean aks4(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getLeft() instanceof Conj) {
                Conj conj1 = (Conj) impl1.getLeft();
                return (impl1.getRight().equals(conj1.getLeft()));
            }
        }
        return false;
    }

    private static boolean aks5(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getLeft() instanceof Conj) {
                Conj conj1 = (Conj) impl1.getLeft();
                return (impl1.getRight().equals(conj1.getRight()));
            }
        }
        return false;
    }

    private static boolean aks6(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getRight() instanceof Disj) {
                Disj disj1 = (Disj) impl1.getRight();
                return (impl1.getLeft().equals(disj1.getLeft()));
            }
        }
        return false;
    }

    private static boolean aks7(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getRight() instanceof Disj) {
                Disj disj1 = (Disj) impl1.getRight();
                return (impl1.getLeft().equals(disj1.getRight()));
            }
        }
        return false;
    }

    private static boolean aks8(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getLeft() instanceof Impl) {
                Impl impl2 = (Impl) impl1.getLeft();
                if (impl1.getRight() instanceof Impl) {
                    Impl impl3 = (Impl) impl1.getRight();
                    if (impl3.getLeft() instanceof Impl) {
                        Impl impl4 = (Impl) impl3.getLeft();
                        if (impl3.getRight() instanceof Impl) {
                            Impl impl5 = (Impl) impl3.getRight();
                            if (impl5.getLeft() instanceof Disj) {
                                Disj disj1 = (Disj) impl5.getLeft();
                                boolean fl1 = impl2.getLeft().equals(disj1.getLeft());
                                boolean fl2 = impl2.getRight().equals(impl4.getRight()) && impl2.getRight().equals(impl5.getRight());
                                boolean fl3 = impl4.getLeft().equals(disj1.getRight());
                                return (fl1 && fl2 && fl3);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean aks9(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl)e;
            if (impl1.getLeft() instanceof Impl){
                Impl impl2 = (Impl)impl1.getLeft();
                if(impl1.getRight() instanceof Impl){
                    Impl impl3 = (Impl)impl1.getRight();
                    if (impl3.getLeft() instanceof Impl){
                        Impl impl4 = (Impl)impl3.getLeft();
                        if (impl4.getRight() instanceof Neg){
                            Neg neg1 = (Neg) impl4.getRight();
                            if (impl3.getRight() instanceof Neg) {
                                Neg neg2 = (Neg) impl3.getRight();
                                boolean fl1 = neg2.getExpr().equals(impl2.getLeft());
                                fl1 = fl1 && neg2.getExpr().equals(impl4.getLeft());
                                boolean fl2 = impl2.getRight().equals(neg1.getExpr());
                                return (fl1 && fl2);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean aks10(Expr e) {
        if (e instanceof Impl) {
            Impl impl1 = (Impl) e;
            if (impl1.getLeft() instanceof Neg) {
                Neg neg1 = (Neg) impl1.getLeft();
                if (neg1.getExpr() instanceof Neg) {
                    Neg neg2 = (Neg) neg1.getExpr();
                    return impl1.getRight().equals(neg2.getExpr());
                }
            }
        }
        return false;
    }

    public static int checkAxioms(Expr e){
        if (aks1(e)) return 1;
        if (aks2(e)) return 2;
        if (aks3(e)) return 3;
        if (aks4(e)) return 4;
        if (aks5(e)) return 5;
        if (aks6(e)) return 6;
        if (aks7(e)) return 7;
        if (aks8(e)) return 8;
        if (aks9(e)) return 9;
        if (aks10(e)) return 10;
        return -1;
    }
}
