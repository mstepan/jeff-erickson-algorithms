package com.max.erickson.dynamic;

import java.util.Arrays;

/**
 * Call a sequence X[1 .. n] of numbers bitonic if there is an index i with
 * 1 < i < n, such that the prefix X[1 .. i] is increasing and the suffix
 * X[i .. n] is decreasing. Describe an efficient algorithm to compute the
 * length of the longest bitonic subsequence of an arbitrary array A of
 * integers.
 */
public class LongestBitonicSubsequence {


    /**
     * Dynamic programming solution.
     * <p>
     * time: O(N^2)
     * space: O(N)
     */
    public static int findLength(int[] arr) {

        int[] increased = longestIncreasedPrefix(arr);
        int[] decreased = longestDecreasedSuffix(arr);

        // set initial 'maxBitonic' value to max increased or max decreased subsequence value,
        // in this way we treat any increased/decreased subsequence as bitonic
        int maxBitonic = Math.max(maxValue(increased), maxValue(decreased));

        for (int i = 1; i < arr.length - 1; ++i) {

            if (increased[i] > 1 && decreased[i] > 1) {
                maxBitonic = Math.max(maxBitonic, increased[i] + decreased[i] - 1);
            }
        }

        return maxBitonic;
    }

    private static int maxValue(int[] arr) {
        return Arrays.stream(arr).max().orElse(Integer.MIN_VALUE);
    }

    /**
     * Find longest increased prefix ending at index.
     * <p>
     * time: O(N^2)
     * space: O(N)
     */
    private static int[] longestIncreasedPrefix(int[] arr) {
        assert arr != null : "null 'arr' parameter passed for 'longestIncreasedPrefix'";

        int[] sol = new int[arr.length];
        sol[0] = 1;

        for (int i = 1; i < sol.length; ++i) {
            int maxEnding = 1;

            for (int j = i - 1; j >= 0; --j) {
                if (arr[j] < arr[i]) {
                    maxEnding = Math.max(maxEnding, sol[j] + 1);
                }
            }

            sol[i] = maxEnding;
        }

        return sol;
    }

    /**
     * Find longest decreased suffix started at index.
     * <p>
     * time: O(N^2)
     * space: O(N)
     */
    private static int[] longestDecreasedSuffix(int[] arr) {
        assert arr != null : "null 'arr' parameter passed for 'longestDecreasedSuffix'";

        int[] sol = new int[arr.length];
        sol[sol.length - 1] = 1;

        for (int i = sol.length - 2; i >= 0; --i) {
            int maxEnding = 1;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    maxEnding = Math.max(maxEnding, sol[j] + 1);
                }
            }

            sol[i] = maxEnding;
        }

        return sol;
    }

}
