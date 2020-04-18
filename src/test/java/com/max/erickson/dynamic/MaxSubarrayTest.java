package com.max.erickson.dynamic;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import static com.max.erickson.dynamic.MaxSubarray.findMaxProduct;
import static com.max.erickson.dynamic.MaxSubarray.findMaxProductBruteforce;
import static com.max.erickson.dynamic.MaxSubarray.findMaxSum;
import static com.max.erickson.dynamic.MaxSubarray.findMaxSumBruteforce;
import static com.max.erickson.dynamic.MaxSubarray.findMaxSumWrapping;
import static com.max.erickson.dynamic.MaxSubarray.maxSumWithRequiredLength;
import static com.max.erickson.dynamic.MaxSubarray.maxSumWithRequiredLengthBruteforce;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class MaxSubarrayTest {

    @Test
    public void maxSumWithRequiredLengthDynamicComparedWithBruteforceRandomArrays() {
        int[] arr = generateRandomArray(100);

        for (int it = 0; it < 10; ++it) {
            for (int i = 0; i < arr.length; ++i) {
                assertThat(maxSumWithRequiredLengthBruteforce(arr, i))
                        .as("dynamic programming value is different from bruteforce for random generated array")
                        .isEqualTo(maxSumWithRequiredLength(arr, i));
            }
        }

    }

    @Test
    public void maxSumWithRequiredLengthDynamicComparedWithBruteforce() {
        int[] arr = {3, -4, -5, 8, 6, -2, -4, -1, 6, -3};

        for (int i = 0; i < arr.length; ++i) {
            assertThat(maxSumWithRequiredLengthBruteforce(arr, i))
                    .as("dynamic programming value is different from bruteforce")
                    .isEqualTo(maxSumWithRequiredLength(arr, i));
        }
    }

    @Test
    public void maxSumWithRequiredLengthNormalCase() {
        int[] arr = {3, -4, -5, 8, 6, -2, -4, -1, 6, -3};

        assertThat(maxSumWithRequiredLength(arr, 7)).isEqualTo(10L);
        assertThat(maxSumWithRequiredLength(arr, 6)).isEqualTo(13L);
        assertThat(maxSumWithRequiredLength(arr, 5)).isEqualTo(13L);
        assertThat(maxSumWithRequiredLength(arr, 4)).isEqualTo(13L);
        assertThat(maxSumWithRequiredLength(arr, 3)).isEqualTo(13L);
        assertThat(maxSumWithRequiredLength(arr, 2)).isEqualTo(14L);
        assertThat(maxSumWithRequiredLength(arr, 1)).isEqualTo(14L);
    }

    @Test
    public void maxSumWithRequiredLengthShouldFailIfRequiredLengthBiggerThanArray() {
        int[] arr = {3, -4, 5};

        assertThatThrownBy(() -> maxSumWithRequiredLength(arr, 4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("arr.length < minLength: 3 < 4");
    }

    @Test
    public void maxSumWithRequiredLengthZeroMinLength() {
        int[] arr = {3, -4, -5, 8, 6, -2, -4, -1, 6, -3};
        assertThat(maxSumWithRequiredLength(arr, 0)).isEqualTo(14L);
    }

    @Test
    public void maxSumWithRequiredLengthFullArray() {
        int[] arr = {3, -4, -5, 8, 6, -2, -4, -1, 6, -3};
        assertThat(maxSumWithRequiredLength(arr, arr.length)).
                isEqualTo(Arrays.stream(arr).asLongStream().sum());
    }

    @Test
    public void findMaxSumWrappingNormalCase() {
        assertThat(findMaxSumWrapping(new int[]{3, -4, 5, 8, 6, -2, -4, -1, 8, -3})).
                as("'findMaxSumWrapping' returned incorrect result, should be prefix + suffix (aka wrapepd subarray)").
                isEqualTo(23);


        assertThat(findMaxSumWrapping(new int[]{3, -4, -5, 8, 6, -2, -4, -1, 8, -3})).
                as("'findMaxSumWrapping' returned incorrect result, should be just ordinary subarray").
                isEqualTo(15);
    }


    @Test
    public void findMaxProductNormalCase() {
        int[] arr = {-6, 12, -7, 0, 14, -7, 5};

        assertThat(findMaxProduct(arr)).
                as("dynamic programming 'findMaxProduct' failed").
                isEqualTo(new BigInteger("504"));

        assertThat(findMaxProductBruteforce(arr)).
                as("bruteforce 'findMaxProductBruteforce' failed").
                isEqualTo(new BigInteger("504"));
    }

    @Test
    public void findMaxProductCheckForRandomArrays() {
        for (int it = 0; it < 100; ++it) {
            int[] arr = generateRandomArray(100);

            assertThat(findMaxProduct(arr)).
                    as("dynamic programming for subarray PRODUCT is different from bruteforce, array %s",
                       Arrays.toString(arr)).
                    isEqualTo(findMaxProductBruteforce(arr));

        }
    }

    @Test
    public void findMaxSumNormalCase() {
        int[] arr = {-3, 8, 4, -9, -5, 12, 6, -1, 0, 8, -5};

        assertThat(findMaxSum(arr)).as("dynamic programming 'findMaxSum' failed").isEqualTo(25);
        assertThat(findMaxSumBruteforce(arr)).as("bruteforce 'findMaxSumBruteforce' failed").isEqualTo(25);
    }

    @Test
    public void findMaxSumCheckForRandomArrays() {

        for (int it = 0; it < 1000; ++it) {
            int[] arr = generateRandomArray(100);

            assertThat(findMaxSum(arr)).as("dynamic programming for subarray SUM is different from bruteforce, array %s",
                                           Arrays.toString(arr)).
                    isEqualTo(findMaxSumBruteforce(arr));

        }
    }

    private static final Random RAND = new Random();

    private static int[] generateRandomArray(int maxExpectedLength) {
        int[] arr = new int[Math.min(10 + RAND.nextInt(maxExpectedLength), maxExpectedLength)];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = -1000 + RAND.nextInt(2000);
        }

        return arr;
    }
}
