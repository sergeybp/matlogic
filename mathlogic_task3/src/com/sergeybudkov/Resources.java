package com.sergeybudkov;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Resources {

    static String[] seq_TRUE_TRUE = {
            "B->A->B",
            "A->B"
    };

    static String[] seq_TRUE_FALSE = {
            "(A->B)->(A->B)->A->B",
            "((A->B)->(A->B)->A->B)->((A->B)->((A->B)->A->B)->A->B)->(A->B)->A->B",
            "((A->B)->((A->B)->A->B)->A->B)->(A->B)->A->B",
            "(A->B)->((A->B)->A->B)->A->B",
            "(A->B)->A->B",
            "A->(A->B)->A",
            "(A->B)->A",
            "((A->B)->A)->((A->B)->A->B)->(A->B)->B",
            "((A->B)->A->B)->(A->B)->B",
            "(A->B)->B",
            "B->!A|B",
            "(B->!A|B)->(A->B)->B->!A|B",
            "(A->B)->B->!A|B",
            "((A->B)->B)->((A->B)->B->!A|B)->(A->B)->!A|B",
            "((A->B)->B->!A|B)->(A->B)->!A|B",
            "(A->B)->!A|B",
            "(!A->A)->(!A->!A)->!!A",
            "A->!A->A",
            "!A->A",
            "(!A->!A)->!!A",
            "!A->!A->!A",
            "(!A->!A->!A)->(!A->(!A->!A)->!A)->!A->!A",
            "(!A->(!A->!A)->!A)->!A->!A",
            "!A->(!A->!A)->!A",
            "!A->!A",
            "!!A",
            "!A->!A->!A",
            "(!A->!A->!A)->(!A->(!A->!A)->!A)->!A->!A",
            "(!A->(!A->!A)->!A)->!A->!A",
            "!A->(!A->!A)->!A",
            "!A->!A",
            "(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B)",
            "((!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B))->!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B)",
            "!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B)",
            "!A->!!A&!B->!A",
            "(!A->!!A&!B->!A)->!A->!A->!!A&!B->!A",
            "!A->!A->!!A&!B->!A",
            "(!A->!A)->(!A->!A->!!A&!B->!A)->!A->!!A&!B->!A",
            "(!A->!A->!!A&!B->!A)->!A->!!A&!B->!A",
            "!A->!!A&!B->!A",
            "(!A->!!A&!B->!A)->(!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B))->!A->(!!A&!B->!!A)->!(!!A&!B)",
            "(!A->(!!A&!B->!A)->(!!A&!B->!!A)->!(!!A&!B))->!A->(!!A&!B->!!A)->!(!!A&!B)",
            "!A->(!!A&!B->!!A)->!(!!A&!B)",
            "!!A&!B->!!A",
            "(!!A&!B->!!A)->!A->!!A&!B->!!A",
            "!A->!!A&!B->!!A",
            "(!A->!!A&!B->!!A)->(!A->(!!A&!B->!!A)->!(!!A&!B))->!A->!(!!A&!B)",
            "(!A->(!!A&!B->!!A)->!(!!A&!B))->!A->!(!!A&!B)",
            "!A->!(!!A&!B)",
            "B->B->B",
            "(B->B->B)->(B->(B->B)->B)->B->B",
            "(B->(B->B)->B)->B->B",
            "B->(B->B)->B",
            "B->B",
            "(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B)",
            "((!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B))->B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B)",
            "B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B)",
            "B->!!A&!B->B",
            "(B->!!A&!B->B)->B->B->!!A&!B->B",
            "B->B->!!A&!B->B",
            "(B->B)->(B->B->!!A&!B->B)->B->!!A&!B->B",
            "(B->B->!!A&!B->B)->B->!!A&!B->B",
            "B->!!A&!B->B",
            "(B->!!A&!B->B)->(B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B))->B->(!!A&!B->!B)->!(!!A&!B)",
            "(B->(!!A&!B->B)->(!!A&!B->!B)->!(!!A&!B))->B->(!!A&!B->!B)->!(!!A&!B)",
            "B->(!!A&!B->!B)->!(!!A&!B)",
            "!!A&!B->!B",
            "(!!A&!B->!B)->B->!!A&!B->!B",
            "B->!!A&!B->!B",
            "(B->!!A&!B->!B)->(B->(!!A&!B->!B)->!(!!A&!B))->B->!(!!A&!B)",
            "(B->(!!A&!B->!B)->!(!!A&!B))->B->!(!!A&!B)",
            "B->!(!!A&!B)",
            "(!A->!(!!A&!B))->(B->!(!!A&!B))->!A|B->!(!!A&!B)",
            "(B->!(!!A&!B))->!A|B->!(!!A&!B)",
            "!A|B->!(!!A&!B)",
            "!!A->!B->!!A&!B",
            "!B->!!A&!B",
            "!!A&!B",
            "!!A&!B->!A|B->!!A&!B",
            "!A|B->!!A&!B",
            "(!A|B->!!A&!B)->(!A|B->!(!!A&!B))->!(!A|B)",
            "(!A|B->!(!!A&!B))->!(!A|B)",
            "!(!A|B)",
            "!(!A|B)->(A->B)->!(!A|B)",
            "(A->B)->!(!A|B)",
            "((A->B)->!A|B)->((A->B)->!(!A|B))->!(A->B)",
            "((A->B)->!(!A|B))->!(A->B)",
            "!(A->B)"
    };

    static String[] seq_FALSE_TRUE = {
            "B->A->B",
            "A->B"
    };

    static String[] seq_FALSE_FALSE = {
            "(!B->A)->(!B->!A)->!!B",
            "((!B->A)->(!B->!A)->!!B)->A->(!B->A)->(!B->!A)->!!B",
            "A->(!B->A)->(!B->!A)->!!B",
            "!A->!B->!A",
            "(!A->!B->!A)->A->!A->!B->!A",
            "A->!A->!B->!A",
            "A->!B->A",
            "(A->!B->A)->A->A->!B->A",
            "A->A->!B->A",
            "A->A->A",
            "(A->A->A)->(A->(A->A)->A)->A->A",
            "(A->(A->A)->A)->A->A",
            "A->(A->A)->A",
            "A->A",
            "!A->A->!A",
            "A->!A",
            "(A->A)->(A->A->!B->A)->A->!B->A",
            "(A->A->!B->A)->A->!B->A",
            "A->!B->A",
            "(A->!A)->(A->!A->!B->!A)->A->!B->!A",
            "(A->!A->!B->!A)->A->!B->!A",
            "A->!B->!A",
            "(A->!B->A)->(A->(!B->A)->(!B->!A)->!!B)->A->(!B->!A)->!!B",
            "(A->(!B->A)->(!B->!A)->!!B)->A->(!B->!A)->!!B",
            "A->(!B->!A)->!!B",
            "(A->!B->!A)->(A->(!B->!A)->!!B)->A->!!B",
            "(A->(!B->!A)->!!B)->A->!!B",
            "A->!!B",
            "!!B->B",
            "(!!B->B)->A->!!B->B",
            "A->!!B->B",
            "(A->!!B)->(A->!!B->B)->A->B",
            "(A->!!B->B)->A->B",
            "A->B"
    };

    static String[] neg_TRUE = new String[]{
            "A->!A->A",
            "!A->A",
            "!A->!A->!A",
            "(!A->!A->!A)->(!A->(!A->!A)->!A)->!A->!A",
            "(!A->(!A->!A)->!A)->!A->!A",
            "!A->(!A->!A)->!A",
            "!A->!A",
            "(!A->A)->(!A->!A)->!!A",
            "(!A->!A)->!!A",
            "!!A"
    };

    static String[] EMPTY = new String[]{};

    static String[] disj_TRUE_TRUE = {
            "A->A|B",
            "A|B"
    };
    static String[] disj_TRUE_FALSE = {
            "A->A|B",
            "A|B"
    };
    static String[] disj_FALSE_TRUE = {
            "B->A|B",
            "A|B"
    };
    static String[] disj_FALSE_FALSE = {
            "A->A->A",
            "(A->A->A)->(A->(A->A)->A)->A->A",
            "(A->(A->A)->A)->A->A",
            "A->(A->A)->A",
            "A->A",
            "(!A&!B->A)->(!A&!B->!A)->!(!A&!B)",
            "((!A&!B->A)->(!A&!B->!A)->!(!A&!B))->A->(!A&!B->A)->(!A&!B->!A)->!(!A&!B)",
            "A->(!A&!B->A)->(!A&!B->!A)->!(!A&!B)",
            "A->!A&!B->A",
            "(A->!A&!B->A)->A->A->!A&!B->A",
            "A->A->!A&!B->A",
            "(A->A)->(A->A->!A&!B->A)->A->!A&!B->A",
            "(A->A->!A&!B->A)->A->!A&!B->A",
            "A->!A&!B->A",
            "(A->!A&!B->A)->(A->(!A&!B->A)->(!A&!B->!A)->!(!A&!B))->A->(!A&!B->!A)->!(!A&!B)",
            "(A->(!A&!B->A)->(!A&!B->!A)->!(!A&!B))->A->(!A&!B->!A)->!(!A&!B)",
            "A->(!A&!B->!A)->!(!A&!B)",
            "!A&!B->!A",
            "(!A&!B->!A)->A->!A&!B->!A",
            "A->!A&!B->!A",
            "(A->!A&!B->!A)->(A->(!A&!B->!A)->!(!A&!B))->A->!(!A&!B)",
            "(A->(!A&!B->!A)->!(!A&!B))->A->!(!A&!B)",
            "A->!(!A&!B)",
            "B->B->B",
            "(B->B->B)->(B->(B->B)->B)->B->B",
            "(B->(B->B)->B)->B->B",
            "B->(B->B)->B",
            "B->B",
            "(!A&!B->B)->(!A&!B->!B)->!(!A&!B)",
            "((!A&!B->B)->(!A&!B->!B)->!(!A&!B))->B->(!A&!B->B)->(!A&!B->!B)->!(!A&!B)",
            "B->(!A&!B->B)->(!A&!B->!B)->!(!A&!B)",
            "B->!A&!B->B",
            "(B->!A&!B->B)->B->B->!A&!B->B",
            "B->B->!A&!B->B",
            "(B->B)->(B->B->!A&!B->B)->B->!A&!B->B",
            "(B->B->!A&!B->B)->B->!A&!B->B",
            "B->!A&!B->B",
            "(B->!A&!B->B)->(B->(!A&!B->B)->(!A&!B->!B)->!(!A&!B))->B->(!A&!B->!B)->!(!A&!B)",
            "(B->(!A&!B->B)->(!A&!B->!B)->!(!A&!B))->B->(!A&!B->!B)->!(!A&!B)",
            "B->(!A&!B->!B)->!(!A&!B)",
            "!A&!B->!B",
            "(!A&!B->!B)->B->!A&!B->!B",
            "B->!A&!B->!B",
            "(B->!A&!B->!B)->(B->(!A&!B->!B)->!(!A&!B))->B->!(!A&!B)",
            "(B->(!A&!B->!B)->!(!A&!B))->B->!(!A&!B)",
            "B->!(!A&!B)",
            "(A->!(!A&!B))->(B->!(!A&!B))->A|B->!(!A&!B)",
            "(B->!(!A&!B))->A|B->!(!A&!B)",
            "A|B->!(!A&!B)",
            "!A->!B->!A&!B",
            "!B->!A&!B",
            "!A&!B",
            "!A&!B->A|B->!A&!B",
            "A|B->!A&!B",
            "(A|B->!A&!B)->(A|B->!(!A&!B))->!(A|B)",
            "(A|B->!(!A&!B))->!(A|B)",
            "!(A|B)"
    };

    static String[] conj_TRUE_TRUE = {
            "A->B->A&B",
            "B->A&B",
            "A&B"
    };
    static String[] conj_TRUE_FALSE = {
            "!B->A&B->!B",
            "A&B->!B",
            "A&B->B",
            "(A&B->B)->(A&B->!B)->!(A&B)",
            "(A&B->!B)->!(A&B)",
            "!(A&B)"
    };
    static String[] conj_FALSE_TRUE = {
            "!A->A&B->!A",
            "A&B->!A",
            "A&B->A",
            "(A&B->A)->(A&B->!A)->!(A&B)",
            "(A&B->!A)->!(A&B)",
            "!(A&B)"
    };
    static String[] conj_FALSE_FALSE = {
            "!B->A&B->!B",
            "A&B->!B",
            "A&B->B",
            "(A&B->B)->(A&B->!B)->!(A&B)",
            "(A&B->!B)->!(A&B)",
            "!(A&B)"
    };

    static String[] AXIOMS = new String[]{
            "A->(B->A)",
            "(A->B)->(A->B->C)->(A->C)",
            "A->B->A&B",
            "A&B->A",
            "A&B->B",
            "A->A|B",
            "B->A|B",
            "(A->C)->(B->C)->(A|B->C)",
            "(A->B)->(A->!B)->!A",
            "!!A->A"
    };


        public static List<Expr> lemma4_4(Expr alpha, Expr beta) {
                Checker checker = new Checker();
                Expr NB = new Neg(beta);
                Expr NA = new Neg(alpha);
                Expr AB = new Sequence(alpha, beta);
                Expr ANB = new Sequence(alpha, NB);
                Expr ANB_NA = new Sequence(ANB, NA);
                Expr NB_ANB = new Sequence(NB, ANB);

                List<Expr> steps = Lists.newArrayList(
                        new Sequence(AB, ANB_NA),
                        AB,
                        ANB_NA,
                        NB_ANB,
                        NB,
                        ANB,
                        NA
                );

                return checker.totalyDeduct(
                        Lists.newArrayList(AB, new Neg(beta)),
                        new Neg(alpha),
                        steps);
        }

        public static List<Expr> lemma4_5(Expr alpha) {
                Expr NA = new Neg(alpha);
                Expr NNA = new Neg(NA);
                Expr AorNA = new Disj(alpha, NA);
                Expr A_AorNA = new Sequence(alpha, AorNA);
                Expr N_AorNA = new Neg(AorNA);
                Expr NN_AorNA = new Neg(N_AorNA);
                Expr NA_AorNA = new Sequence(NA, AorNA);
                Expr N_AorNA__NA = new Sequence(N_AorNA, NA);
                Expr N_AorNA__NNA = new Sequence(N_AorNA, NNA);
                Expr N_AorNA__NNA______NN_AorNA = new Sequence(N_AorNA__NNA, NN_AorNA);

                List<Expr> result = new ArrayList<Expr>();

                result.add(A_AorNA);
                result.addAll(lemma4_4(alpha, AorNA));
                result.add(N_AorNA__NA);

                result.add(NA_AorNA);
                result.addAll(lemma4_4(NA, AorNA));
                result.add(N_AorNA__NNA);

                result.add(new Sequence(N_AorNA__NA, N_AorNA__NNA______NN_AorNA));
                result.add(N_AorNA__NNA______NN_AorNA);
                result.add(NN_AorNA);
                result.add(new Sequence(NN_AorNA, AorNA));
                result.add(AorNA);
                return result;
        }

        public static Checker.ProofWithAssumptions lemma4_6(Checker.ProofWithAssumptions ifP,
                                                      Checker.ProofWithAssumptions ifNP) {
                List<Expr> steps = new ArrayList<Expr>();
                Checker checker = new Checker();
                Checker.ProofWithAssumptions fromPtoA = checker.useDeductionConvertion(ifP);
                checker = new Checker();
                Checker.ProofWithAssumptions fromNPtoA = checker.useDeductionConvertion(ifNP);

                Expr P = ifP.alphaAssum;
                Expr A = ifP.toBeProofed;

                Expr NP = new Neg(P);
                Expr NP_A = new Sequence(NP, A);
                Expr PorNP = new Disj(P, NP);
                Expr PorNP_A = new Sequence(PorNP, A);
                Expr NP_A____PorNP_A = new Sequence(NP_A, PorNP_A);

                steps.addAll(fromPtoA.steps);
                steps.addAll(fromNPtoA.steps);
                steps.addAll(Resources.lemma4_5(P));
                steps.add(new Sequence(new Sequence(P, A), NP_A____PorNP_A));
                steps.add(NP_A____PorNP_A);
                steps.add(PorNP_A);
                steps.add(A);

                List<Expr> assumptions = new ArrayList<Expr>();
                for (int i = 0; i < ifP.assumptions.size() - 1; i++) {
                        assumptions.add(ifP.assumptions.get(i));
                }
                Expr alpha = null;
                if (ifP.assumptions.size() >= 1) {
                        alpha = ifP.assumptions.get(ifP.assumptions.size() - 1);
                }
                return new Checker.ProofWithAssumptions(assumptions, alpha, A, steps);
        }

        public static Checker.ProofWithAssumptions getProof(int varIndex, Expr exp, List<String> varNames, Map<String, Boolean> varValues) {
                if (varIndex < varNames.size()) {
                        varValues.put(varNames.get(varIndex), false);
                        Checker.ProofWithAssumptions proofF = getProof(varIndex + 1, exp, varNames, varValues);
                        varValues.put(varNames.get(varIndex), true);
                        Checker.ProofWithAssumptions proofT = getProof(varIndex + 1, exp, varNames, varValues);
                        return lemma4_6(proofT, proofF);
                } else {
                        List<Expr> assums = new ArrayList<Expr>();
                        for (int i = 0; i < varNames.size() - 1; i++) {
                                String name = varNames.get(i);
                                if (varValues.get(name)) {
                                        assums.add(new Var(name));
                                } else {
                                        assums.add(new Neg(new Var(name)));
                                }
                        }
                        Expr alphaAsum;
                        String name = varNames.get(varValues.size() - 1);
                        if (varValues.get(name)) {
                                alphaAsum = new Var(name);
                        } else {
                                alphaAsum = new Neg(new Var(name));
                        }
                        List<Expr> steps = new ArrayList<Expr>();
                        addSteps(exp, varValues, steps);
                        return new Checker.ProofWithAssumptions(assums, alphaAsum, exp, steps);
                }
        }
        public static void addSteps(Expr exp, Map<String, Boolean> varValues, List<Expr> steps) {
                exp.addSteps(varValues, steps);
        }



}
