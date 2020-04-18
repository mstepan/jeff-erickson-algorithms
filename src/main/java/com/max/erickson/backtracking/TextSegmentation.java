package com.max.erickson.backtracking;

import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public final class TextSegmentation {

    private TextSegmentation() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * time: O(2^N)
     * space: O(N)
     */
    public static int countSegmentations(String text, Set<String> dictionary) {
        checkNotNull(text, "null 'text' parameter passed");
        checkNotNull(dictionary, "null 'dictionary' parameters passed");

        if (text.length() == 0) {
            return 1;
        }

        int[] res = {0};

        int maxWordLength = findMaxWordLength(dictionary);

        assert maxWordLength >= 0 : "negative 'maxWordLength' detected: " + maxWordLength;

        countSegmentationsRec(text.toLowerCase().toCharArray(), 0, toLowerCase(dictionary), res, maxWordLength);

        return res[0];
    }

    private static Set<String> toLowerCase(Set<String> dictionary) {
        return dictionary.stream().map(String::toLowerCase).collect(Collectors.toSet());
    }

    private static int findMaxWordLength(Set<String> dictionary) {
        return dictionary.stream().
                map(String::length).
                max(Integer::compare).
                orElse(0);
    }

    private static void countSegmentationsRec(char[] arr, int index, Set<String> dictionary, int[] cnt, int maxWordLength) {

        if (index == arr.length) {
            ++cnt[0];
            return;
        }

        StringBuilder curSegment = new StringBuilder(maxWordLength);

        for (int i = index; i < arr.length && curSegment.length() <= maxWordLength; ++i) {

            curSegment.append(arr[i]);

            if (dictionary.contains(curSegment.toString())) {
                countSegmentationsRec(arr, i + 1, dictionary, cnt, maxWordLength);
            }
        }
    }

}
