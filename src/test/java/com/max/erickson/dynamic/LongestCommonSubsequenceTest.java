package com.max.erickson.dynamic;

import com.max.erickson.backtracking.LongestCommonSubsequenceBacktracking;
import org.junit.Test;

import java.util.Random;

import static com.max.erickson.dynamic.LongestCommonSubsequence.findLength;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LongestCommonSubsequenceTest {

    @Test
    public void findLCSAndCompareDynamicWithBacktracking() {
        for (int it = 0; it < 100; ++it) {
            char[] first = createRandomDNA(5, 20);
            char[] second = createRandomDNA(5, 20);

            assertThat(findLength(first, second))
                    .as("dynamic programming solituion is different from backtracking, dynamic length: %")
                    .isEqualTo(LongestCommonSubsequenceBacktracking.
                            findLongestCommonSubsequence(new String(first), new String(second)).length());
        }
    }

    @Test
    public void findLCSNormalCase() {
        char[] first = "AGGTAB".toCharArray();
        char[] second = "GXTXAYB".toCharArray();

        assertThat(findLength(first, second)).isEqualTo(4);
    }

    @Test
    public void findLCSFirstArrayEmpty() {
        char[] first = "".toCharArray();
        char[] second = "AGGTA".toCharArray();

        assertThat(findLength(first, second)).
                isEqualTo(0);
    }

    @Test
    public void findLCSSecondArrayEmpty() {
        char[] first = "GXTXA".toCharArray();
        char[] second = "".toCharArray();

        assertThat(findLength(first, second)).
                isEqualTo(0);
    }

    @Test
    public void findLCSBothArraysEmpty() {
        char[] first = "".toCharArray();
        char[] second = "".toCharArray();

        assertThat(findLength(first, second)).
                isEqualTo(0);
    }

    private static final Random RAND = new Random();

    private static final char[] DNA_LETTERS = {'A', 'C', 'G', 'T'};

    private static char[] createRandomDNA(int minLength, int maxLength) {

        char[] data = new char[minLength + RAND.nextInt(maxLength - minLength)];

        for (int i = 0; i < data.length; ++i) {
            data[i] = DNA_LETTERS[RAND.nextInt(DNA_LETTERS.length)];
        }

        return data;
    }

}
