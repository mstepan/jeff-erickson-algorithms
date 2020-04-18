package com.max.erickson.dynamic;

import com.max.erickson.backtracking.LongestBitonicSubSequenceBacktracking;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LongestBitonicSubsequenceTest {

    @Test
    public void findBitonicCompareDynamicAndBacktracking() {

        for (int it = 0; it < 100; ++it) {
            int[] arr = generateRandomArray(5, 15);

            int dynamicLength = LongestBitonicSubsequence.findLength(arr);
            int bruteforceLength = LongestBitonicSubSequenceBacktracking.findBruteforce(arr).size();

            assertThat(dynamicLength)
                    .as("longest bitonic subsequence are different for dynamic & backtracking solutions for array %s, " +
                                "dynamic %s, bruteforce %s",
                        Arrays.toString(arr), dynamicLength, bruteforceLength)
                    .isEqualTo(bruteforceLength);
        }
    }

    @Test
    public void findBitonicSubsequenceNormalCase() {

        int[] arr = {3, 2, 8, -4, 6, 15, 7, 8};

        assertThat(LongestBitonicSubsequence.findLength(arr)).
                isEqualTo(4);
    }

    @Test
    public void findBitonicWithIncreasedAsMax() {

        int[] arr = {-48, 18, -58, -55, 66, 89};

        assertThat(LongestBitonicSubsequence.findLength(arr)).
                isEqualTo(4);
    }

    private static final Random RAND = new Random();

    private static int[] generateRandomArray(int minLength, int maxLength) {
        int[] arr = new int[minLength + RAND.nextInt(maxLength - minLength)];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = -1000 + RAND.nextInt(2000);
        }

        return arr;
    }
}
