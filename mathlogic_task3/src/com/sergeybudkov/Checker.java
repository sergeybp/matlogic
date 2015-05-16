package com.sergeybudkov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Checker {

    private static final String[] unparsedAxioms = Resources.AXIOMS;

    private final List<Expr> axioms;
    private final List<Expr> proofedExpressions;
    private final List<Expr> assumptions;

    public Checker() {
        this.axioms = new ArrayList<Expr>(unparsedAxioms.length);
        this.proofedExpressions = new ArrayList<Expr>();
        this.assumptions = new ArrayList<Expr>();
        PatternParser parser = new PatternParser();
        for (String axiom : unparsedAxioms) {
            axioms.add(parser.parseExpression(axiom));
        }
    }

    private boolean isCorespondsToAxiom(Expr expression) {
        for (Expr axiom : axioms) {
            if (expression.compareToPattern(axiom, new HashMap<String, Expr>())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsIn(List<Expr> expressions, Expr expression) {
        for (Expr exp : expressions) {
            if (expression.compareToExpression(exp)) {
                return true;
            }
        }
        return false;
    }

    private boolean wasProofed(Expr expression) {
        return containsIn(proofedExpressions, expression);
    }

    private boolean isProofedByModusPonons(Expr expression) {
        for (Expr proofed : proofedExpressions) {
            if (proofed.getClass() == Sequence.class) {
                Sequence proofedSequence = (Sequence) proofed;
                if (expression.compareToExpression(proofedSequence.getRight())
                        && wasProofed(proofedSequence.getLeft())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class ProofWithAssumptions {
        public List<Expr> assumptions;
        public Expr alphaAssum;
        public Expr toBeProofed;
        public List<Expr> steps;

        public ProofWithAssumptions(List<Expr> assumptions, Expr alphaAssum, Expr toBeProofed, List<Expr> steps) {
            this.assumptions = assumptions;
            this.alphaAssum = alphaAssum;
            this.toBeProofed = toBeProofed;
            this.steps = steps;
        }
    }

    public List<Expr> totalyDeduct(List<Expr> assumptions, Expr toBeProofed, List<Expr> steps) {
        ProofWithAssumptions proof = new ProofWithAssumptions(
                assumptions.subList(0, assumptions.size() - 1), assumptions.get(assumptions.size() - 1), toBeProofed, steps);
        while (proof.alphaAssum != null) {
            proof = new Checker().useDeductionConvertion(proof);
        }
        return proof.steps;
    }

    public ProofWithAssumptions useDeductionConvertion(ProofWithAssumptions proof) {
        List<Expr> resAssumptions = new ArrayList<Expr>();
        Expr resAlpha = null;
        Expr resToBeProofed = null;
        List<Expr> resSteps = new ArrayList<Expr>();
        for (Expr ass : proof.assumptions) {
            assumptions.add(ass);
        }
        StringBuilder firstLine = new StringBuilder();
        for (int i = 0; i < assumptions.size() - 1; i++) {
            resAssumptions.add(assumptions.get(i));
        }
        if (assumptions.size() >= 1) {
            resAlpha = assumptions.get(assumptions.size() - 1);
        }
        resToBeProofed = new Sequence(proof.alphaAssum, proof.toBeProofed);
        int firstIncorrectLine = -1;
        int lineNumber = 0;
        for (Expr expI : proof.steps) {
            if (firstIncorrectLine == -1) {
                proofedExpressions.add(expI);
                if (isCorespondsToAxiom(expI) || containsIn(assumptions, expI)) {
                    Expr alphaConsSigma = new Sequence(proof.alphaAssum, expI);
                    resSteps.add(expI);
                    resSteps.add(new Sequence(expI, alphaConsSigma));
                    resSteps.add(alphaConsSigma);
                } else if (expI.compareToExpression(proof.alphaAssum)) {
                    Expr AA = new Sequence(proof.alphaAssum, proof.alphaAssum);
                    Expr A_AA = new Sequence(proof.alphaAssum, AA);
                    Expr AA_A = new Sequence(AA, proof.alphaAssum);
                    Expr A__AA_A = new Sequence(proof.alphaAssum, AA_A);
                    Expr lemma1 = A_AA;
                    Expr lemma2 = new Sequence(A_AA, new Sequence(A__AA_A, AA));
                    Expr lemma3 = new Sequence(A__AA_A, AA);
                    Expr lemma4 = A__AA_A;
                    Expr lemma5 = AA;
                    resSteps.add(lemma1);
                    resSteps.add(lemma2);
                    resSteps.add(lemma3);
                    resSteps.add(lemma4);
                    resSteps.add(lemma5);
                } else {
                    Expr expK = null;
                    Expr expJ = null;
                    for (Expr proofed : proofedExpressions) {
                        if (proofed.getClass() == Sequence.class) {
                            Sequence proofedSequence = (Sequence) proofed;
                            if (expI.compareToExpression(proofedSequence.getRight())
                                    && wasProofed(proofedSequence.getLeft())) {
                                expK = proofedSequence;
                                expJ = proofedSequence.getLeft();
                                break;
                            }
                        }
                    }
                    if (expK == null || expJ == null) {
                        firstIncorrectLine = lineNumber;
                    } else {
                        Expr res2 = new Sequence(new Sequence(proof.alphaAssum, new Sequence(expJ, expI)),
                                new Sequence(proof.alphaAssum, expI));
                        Expr res1 = new Sequence(new Sequence(proof.alphaAssum, expJ), res2);
                        resSteps.add(res1);
                        resSteps.add(res2);
                        resSteps.add(new Sequence(proof.alphaAssum, expI));
                    }
                }
            }
            lineNumber++;
        }
        if (firstIncorrectLine != -1) {
            return null;
        } else {
            return new ProofWithAssumptions(resAssumptions, resAlpha, resToBeProofed, resSteps);
        }
    }

    public List<String> useDeductionConvertion(List<String> source) {
        Parser parser = new Parser();
        String[] lineParts = source.get(0).split("(,|\\|-)");
        List<Expr> exprAssums = new ArrayList<Expr>();
        for (int i = 0; i < lineParts.length - 2; i++) {
            exprAssums.add(parser.parseExpression(lineParts[i]));
        }
        Expr alphaAssum = parser.parseExpression(lineParts[lineParts.length - 2]);
        Expr toBeProofed = parser.parseExpression(lineParts[lineParts.length - 1]);
        List<Expr> steps = new ArrayList<Expr>();
        for (int i = 1; i < source.size(); i++) {
            steps.add(parser.parseExpression(source.get(i)));
        }
        ProofWithAssumptions newProof = useDeductionConvertion(new ProofWithAssumptions(exprAssums, alphaAssum, toBeProofed, steps));
        List<String> result = new ArrayList<String>();
        StringBuilder firstLine = new StringBuilder();
        for (Expr ass : newProof.assumptions) {
            firstLine.append(ass + ",");
        }
        if (newProof.alphaAssum != null) {
            firstLine.append(newProof.alphaAssum + "|-" + newProof.toBeProofed);
            result.add(firstLine.toString());
        }
        for (Expr step : newProof.steps) {
            result.add(step.toString());
        }
        result.add("");
        return result;
    }


}
