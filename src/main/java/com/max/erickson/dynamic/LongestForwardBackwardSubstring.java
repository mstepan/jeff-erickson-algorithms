package com.max.erickson.dynamic;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise 8.
 * <p>
 * Describe and analyze an efficient algorithm to find the length of the longest
 * contiguous substring that appears both forward and backward in an input
 * string T[1 .. n]. The forward and backward substrings must not overlap.
 * <p>
 * Example:
 * <p>
 * Given the input string REDIVIDE, your algorithm should return 3, for the
 * substring EDI. (The forward and backward substrings must not overlap!)
 */
public final class LongestForwardBackwardSubstring {

    private LongestForwardBackwardSubstring() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    /**
     * N = str.length
     * <p>
     * time: O(N^2)
     * space: O(N)
     */
    public static int findLength(String str) {
        checkArgument(str != null, "null 'str' parameter detected");

        // empty or single symbol string should  0, as a result
        if (str.length() < 2) {
            return 0;
        }

        final char[] arr = str.toCharArray();
        final char lastCh = arr[arr.length - 1];

        int[] prev = new int[arr.length];
        int maxSoFar = 0;

        for (int i = 0; i < arr.length - 1; ++i) {
            char ch = arr[i];

            int[] cur = new int[arr.length];
            cur[0] = (arr[i] == lastCh) ? 1 : 0;
            maxSoFar = Math.max(maxSoFar, cur[0]);

            for (int j = 1; j < arr.length - 1 - i; ++j) {
                char revCh = arr[arr.length - 1 - j];

                if (ch == revCh) {
                    cur[j] = 1 + prev[j - 1];
                    maxSoFar = Math.max(maxSoFar, cur[j]);
                }
            }
            prev = cur;
        }

        return maxSoFar;
    }

}
