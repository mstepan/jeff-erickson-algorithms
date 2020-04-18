package com.max.erickson.dynamic;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Basic arithmetic expression is composed of characters from the set
 * {1,+,*} and parentheses. Almost every integer can be represented by more
 * than one basic arithmetic expression.
 * Describe and analyze an algorithm to compute, given an integer n as input,
 * the minimum number of 1s in a basic arithmetic expression whose value is
 * equal to n. The number of parentheses doesn't matter, just the number of
 * 1s.
 */
public final class BasicArithmeticExpression {

    private BasicArithmeticExpression() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Dynamic programming solution for shortest basic arithmetic expression.
     * time: O(N*K)
     * space: O(N)
     * <p>
     * N = baseValue
     * K = number of ways to sum or multiply number
     */
    public static String findShortestExpression(int baseValue) {
        checkArgument(baseValue > 0, "Negative value or zero passed: %s", baseValue);

        if (baseValue == 1) {
            return "1";
        }

        int[] optimal = new int[baseValue + 1];
        optimal[1] = 1;
        optimal[2] = 2;
        optimal[3] = 3;

        String[] optimalExp = new String[baseValue + 1];
        optimalExp[1] = "1";
        optimalExp[2] = "1 + 1";
        optimalExp[3] = "1 + 1 + 1";

        for (int value = 4; value < optimal.length; ++value) {

            int bestSoFar = Integer.MAX_VALUE;
            String bestExp = "";

            // check all possible summations
            for (int left = 1; left <= value / 2; ++left) {

                int right = value - left;

                int curOpt = optimal[left] + optimal[right];

                if (curOpt < bestSoFar) {
                    bestSoFar = curOpt;
                    bestExp = String.format("(%s) + (%s)", optimalExp[left], optimalExp[right]);
                }
            }

            // check all multiplications
            for (int left = 2; left < value; ++left) {
                if (value % left == 0) {
                    int right = value / left;

                    int curOpt = optimal[left] + optimal[right];

                    if (curOpt < bestSoFar) {
                        bestSoFar = curOpt;
                        bestExp = String.format("(%s) * (%s)", optimalExp[left], optimalExp[right]);
                    }
                }
            }

            optimal[value] = bestSoFar;
            optimalExp[value] = bestExp;
        }

        return optimalExp[optimalExp.length - 1];
    }

}
