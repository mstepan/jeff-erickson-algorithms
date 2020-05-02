package com.max.erickson.dynamic;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise 9a.
 * <p>
 * A palindrome is any string that is exactly the same as its reversal, like I, or
 * DEED, or RACECAR, or AMANAPLANACATACANALPANAMA.
 * <p>
 * Describe and analyze an algorithm to find the length of the longest
 * subsequence of a given string that is also a palindrome.
 * For example, the longest palindrome subsequence of the string
 * MAHDYNAMICPROGRAMZLETMESHOWYOUTHEM is MHYMRORMYHM; thus, given
 * that string as input, your algorithm should return 11.
 */
public final class LongestPalindromeSubsequence {

    private LongestPalindromeSubsequence() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    /**
     * Dynamic programming solution.
     * <p>
     * LP[i][j] =
     * => | if i == j: 1
     * => | else if str[i] == str[j]: 2 + LP[i+1][j-1]
     * => | else: max( LP[i+1][j], LP[i][j-1] )
     * <p>
     * time: O(N^2)
     * space: O(N^2), can be reduced to O(N)
     */
    public static int findLongest(String str) {
        checkArgument(str != null, "null 'str' argument detected");

        final int size = str.length();
        int[][] sol = new int[size][size];

        for (int row = size - 1; row >= 0; --row) {

            sol[row][row] = 1;

            for (int col = row + 1; col < size; ++col) {
                if (str.charAt(row) == str.charAt(col)) {
                    sol[row][col] = 2 + sol[row + 1][col - 1];
                }
                else {
                    sol[row][col] = Math.max(sol[row + 1][col], sol[row][col - 1]);
                }
            }
        }

        return sol[0][size - 1];
    }
}
