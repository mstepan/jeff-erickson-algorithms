package com.max.erickson.dynamic;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public final class TextSegmentation {

    private TextSegmentation() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     *
     * Given an array A[1 .. n] of characters, compute the number of partitions
     * of A into words. For example, given the string ARTISTOIL, your algorithm
     * should return 2, for the partitions ARTIST·OIL and ART·IS·TOIL.
     *
     * Count the ways that text can be partitioned into words using dynamic programming approach.
     * <p>
     * N = text.length
     * M = dictionary.size
     * <p>
     * time: O(N^2)
     * space: O(M)
     */
    public static int countWays(String text, Set<String> dictionary) {

        checkArgument(text != null, "null 'text' argument passed");
        checkArgument(dictionary != null, "null 'dictionary' argument passed");

        int[] sol = new int[text.length() + 1];
        sol[0] = 1;

        // this value will terminate inner loop as soon as we reached max possible word length from dictionary
        final int maxWordLength = dictionary.stream().
                map(String::length).
                max(Integer::compareTo).
                orElse(Integer.MAX_VALUE);

        for (int to = 0; to < text.length(); ++to) {

            for (int from = to; from >= 0 && (to - from + 1) <= maxWordLength; --from) {

                String word = text.substring(from, to + 1);

                if (dictionary.contains(word)) {
                    sol[to + 1] += sol[from];
                }
            }
        }

        return sol[sol.length - 1];
    }
}
