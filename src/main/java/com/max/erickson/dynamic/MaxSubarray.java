package com.max.erickson.dynamic;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public final class MaxSubarray {

    private MaxSubarray() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Find max subarray product using dynamic programming approach.
     * <p>
     * N = arr.length
     * <p>
     * time: O(N), if we neglect the fact that BigInteger multiplication is not O(1)
     * space: O(1)
     */
    public static BigInteger findMaxProduct(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        //Use BigInteger here to prevent overflow/underflow during multiplication
        BigInteger maxSoFar = BigInteger.ZERO;

        BigInteger positive = BigInteger.ZERO;
        BigInteger negative = BigInteger.ZERO;

        for (int value : arr) {

            if (value == 0) {
                positive = BigInteger.ZERO;
                negative = BigInteger.ZERO;
            }
            else if (value > 0) {
                if (BigInteger.ZERO.equals(positive)) {
                    positive = BigInteger.valueOf(value);
                }
                else {
                    positive = positive.multiply(BigInteger.valueOf(value));
                }

                negative = negative.multiply(BigInteger.valueOf(value));
            }
            // value < 0
            else {
                if (BigInteger.ZERO.equals(negative)) {
                    negative = (BigInteger.ZERO.equals(positive) ?
                            BigInteger.valueOf(value) : positive.multiply(BigInteger.valueOf(value)));
                    positive = BigInteger.ZERO;
                }
                else {

                    BigInteger newPositive = negative.multiply(BigInteger.valueOf(value));

                    negative = (BigInteger.ZERO.equals(positive) ?
                            BigInteger.valueOf(value) : positive.multiply(BigInteger.valueOf(value)));
                    positive = newPositive;
                }
            }

            maxSoFar = maxSoFar.compareTo(positive) >= 0 ? maxSoFar : positive;
        }

        return maxSoFar;
    }

    /**
     * Find max subarray product using bruteforce approach.
     * <p>
     * time: O(N^2), if we neglect the fact that BigInteger multiplication is not O(1)
     * space: O(1)
     */
    public static BigInteger findMaxProductBruteforce(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        //Use BigInteger here to prevent overflow/underflow during multiplication
        BigInteger maxSoFar = BigInteger.ZERO;

        for (int i = 0; i < arr.length; ++i) {
            BigInteger curSum = BigInteger.ONE;

            for (int j = i; j < arr.length; ++j) {
                curSum = curSum.multiply(BigInteger.valueOf(arr[j]));
                maxSoFar = maxSoFar.compareTo(curSum) >= 0 ? maxSoFar : curSum;
            }
        }

        return maxSoFar;
    }

    /**
     * Max subarray sum variant: find a contiguous subarray of length at least 'minLength' that has the largest sum.
     * <p>
     * N = arr.length
     * L = minLength value
     * <p>
     * time: O(N*L)
     * space: O(L)
     */
    public static long maxSumWithRequiredLength(int[] arr, int minLength) {
        checkMinRequiredLengthPreconditions(arr, minLength);

        return findMinRequiredLengthSolutionForCornerCases(arr, minLength).
                orElseGet(() -> maxSumWithRequiredLengthNormalCase(arr, minLength));

    }

    private static long maxSumWithRequiredLengthNormalCase(int[] arr, int minLength) {
        long maxSoFar = Long.MIN_VALUE;
        long[] prevPrefix = createEmptyPrefixArray(minLength);

        for (int value : arr) {

            long[] curPrefix = createEmptyPrefixArray(minLength);

            for (int i = 1; i < curPrefix.length; ++i) {
                curPrefix[i] = Math.max(prevPrefix[i - 1], prevPrefix[i]) + value;
            }

            maxSoFar = Math.max(maxSoFar, curPrefix[curPrefix.length - 1]);

            prevPrefix = curPrefix;
        }

        return maxSoFar;
    }

    /**
     * Max subarray sum with min required length using bruteforce approach.
     * <p>
     * time: O(N^2)
     * space: O(1)
     */
    public static long maxSumWithRequiredLengthBruteforce(int[] arr, int minLength) {
        checkMinRequiredLengthPreconditions(arr, minLength);

        return findMinRequiredLengthSolutionForCornerCases(arr, minLength).
                orElseGet(() -> maxSumWithRequiredLengthBruteforceNormalCase(arr, minLength));
    }


    private static long maxSumWithRequiredLengthBruteforceNormalCase(int[] arr, int minLength) {

        long maxSoFar = Long.MIN_VALUE;

        for (int end = minLength - 1; end < arr.length; ++end) {
            long cur = 0L;
            int prefixLength = 0;

            for(int i= end; i >=0; --i){
                cur += arr[i];
                ++prefixLength;

                if( prefixLength >= minLength){
                    maxSoFar = Math.max(maxSoFar, cur);
                }
            }
        }

        return maxSoFar;
    }

    private static void checkMinRequiredLengthPreconditions(int[] arr, int minLength) {
        checkArgument(arr != null, "null 'arr' argument passed");
        checkArgument(arr.length >= minLength, "arr.length < minLength: %s < %s", arr.length, minLength);
    }

    private static Optional<Long> findMinRequiredLengthSolutionForCornerCases(int[] arr, int minLength) {
        if (minLength == 0) {
            // minLength is zero, so no restrictions on length, use standard algorithm
            return Optional.of(findMaxSum(arr));
        }

        if (arr.length == minLength) {
            return Optional.of(Arrays.stream(arr).asLongStream().sum());
        }

        return Optional.empty();
    }


    private static long[] createEmptyPrefixArray(int minLength) {
        long[] prefix = new long[minLength + 1];
        for (int i = 1; i < prefix.length; ++i) {
            prefix[i] = Integer.MIN_VALUE;
        }

        return prefix;
    }

    /**
     * Wrapping around: Suppose A is a circular array. In this setting, a
     * "contiguous subarray" can be either an interval A[i .. j] or a suffix followed
     * by a prefix A[i .. n]  A[1 .. j]. Describe and analyze an algorithm that
     * finds a contiguous subarray of A with the largest sum.
     * <p>
     * N = arr.length
     * <p>
     * time: O(N)
     * space: O(N), because we need to store 'maxPrefix'
     */
    public static long findMaxSumWrapping(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        long maxSum = findMaxSum(arr);

        long[] maxPrefix = calculateMaxPrefix(arr);

        long maxSumWrapping = 0L;

        long suffix = 0L;

        for (int i = arr.length - 1; i > 0; --i) {
            suffix += arr[i];
            maxSumWrapping = Math.max(maxSumWrapping, suffix + maxPrefix[i - 1]);
        }

        return Math.max(maxSum, maxSumWrapping);
    }

    private static long[] calculateMaxPrefix(int[] arr) {
        assert arr != null : "null 'arr' parameter passed";

        if (arr.length == 0) {
            return new long[]{};
        }

        long[] maxPrefix = new long[arr.length];
        maxPrefix[0] = arr[0];

        int cur = arr[0];

        for (int i = 1; i < arr.length; ++i) {
            cur += arr[i];
            maxPrefix[i] = Math.max(maxPrefix[i - 1], cur);
        }
        return maxPrefix;
    }

    /**
     * Find max subarray sum using dynamic programming approach.
     * <p>
     * N = arr.length
     * <p>
     * time: O(N)
     * space: O(1)
     */
    public static long findMaxSum(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        // use long here to prevent integer overflow
        long maxSoFar = 0L;
        long maxCur = 0L;

        for (int value : arr) {
            maxCur = Math.max(maxCur + value, 0L);
            maxSoFar = Math.max(maxSoFar, maxCur);
        }

        return maxSoFar;
    }

    /**
     * Find max subarray sum using bruteforce approach.
     * <p>
     * time: O(N^2), because we need to consider all possible pair of indexes for subarrays.
     * space: O(1)
     */
    public static long findMaxSumBruteforce(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        long maxSoFar = 0L;

        for (int i = 0; i < arr.length; ++i) {
            long curSum = 0L;

            for (int j = i; j < arr.length; ++j) {
                curSum += arr[j];
                maxSoFar = Math.max(maxSoFar, curSum);
            }
        }

        return maxSoFar;
    }
}
