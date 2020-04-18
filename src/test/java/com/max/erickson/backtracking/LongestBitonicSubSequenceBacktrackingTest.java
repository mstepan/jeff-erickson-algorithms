package com.max.erickson.backtracking;

import org.junit.Test;

import java.util.Random;

import static com.max.erickson.backtracking.LongestBitonicSubSequenceBacktracking.find;
import static com.max.erickson.backtracking.LongestBitonicSubSequenceBacktracking.findBruteforce;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LongestBitonicSubSequenceBacktrackingTest {

    private static final Random RAND = new Random();

    @Test
    public void findLongestBitonicSubSequenceNormalCases() {
        for (int i = 0; i < 100; ++i) {
            int[] arr = generateRandomArray(2 + RAND.nextInt(20));
            assertThat(find(arr)).isEqualTo(findBruteforce(arr));
        }
    }

    private static int[] generateRandomArray(int length) {

        int[] arr = new int[length];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = RAND.nextInt(100);
        }

        return arr;
    }

}
