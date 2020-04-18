package com.max.erickson.dynamic;

import static com.google.common.base.Preconditions.checkArgument;

public class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Calculate the length of longest common subsequence(LCS) using dynamic programming approach.
     * <p>
     * N = first.length
     * M = second.length
     * <p>
     * time: O(N*M)
     * space: O(N*M), can be reduced to O(min(N, M))
     */
    public static int findLength(char[] first, char[] second) {
        checkArgument(first != null, "'first' array parameter is null");
        checkArgument(second != null, "'second' array parameter is null");

        if (first.length == 0 || second.length == 0) {
            return 0;
        }

        final int rowsCount = first.length + 1;
        final int colsCount = second.length + 1;

        int[][] sol = new int[rowsCount][colsCount];

        for (int row = 1; row < rowsCount; ++row) {
            for (int col = 1; col < colsCount; ++col) {
                if (first[row - 1] == second[col - 1]) {
                    sol[row][col] = 1 + sol[row - 1][col - 1];
                }
                else {
                    sol[row][col] = Math.max(sol[row][col - 1], sol[row - 1][col]);
                }
            }
        }

        return sol[rowsCount - 1][colsCount - 1];
    }

}
