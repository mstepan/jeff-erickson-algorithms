package com.max.erickson.backtracking;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public final class TextSegmentationForTwo {

    private TextSegmentationForTwo() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * time: O(2^N)
     * space: O(N)
     */
    public static boolean canBePartition(String first, String second, Set<String> dictionary) {
        checkNotNull(first, "null 'first' parameter passed");
        checkNotNull(second, "null 'second' parameter passed");
        checkNotNull(dictionary, "null 'dictionary' parameters passed");

        if (first.length() != second.length()) {
            return false;
        }

        return canBePartitionRec(first.toCharArray(), second.toCharArray(), 0, dictionary);
    }

    private static boolean canBePartitionRec(char[] first, char[] second, int index, Set<String> dictionary) {

        if (index == first.length) {
            return true;
        }

        StringBuilder firstWord = new StringBuilder();
        StringBuilder secondWord = new StringBuilder();

        for (int i = index; i < first.length; ++i) {
            firstWord.append(first[i]);
            secondWord.append(second[i]);

            if (dictionary.contains(firstWord.toString()) && dictionary.contains(secondWord.toString())) {
                boolean res = canBePartitionRec(first, second, i + 1, dictionary);
                if (res) {
                    return true;
                }
            }
        }

        return false;
    }


}
