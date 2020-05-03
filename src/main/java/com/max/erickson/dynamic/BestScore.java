package com.max.erickson.dynamic;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise 13.
 * <p>
 * It’s almost time to show off your sweet dancing skills! Tomorrow is
 * the big dance contest you’ve been training for your entire life.
 * You know all the songs, all the judges, and your own dancing ability
 * extremely well. For each integer k, you know that if you dance to the kth
 * song on the schedule, you will be awarded exactly Score[k] points, but then
 * you will be physically unable to dance for the next Wait[k] songs (that is,
 * you cannot dance to songs k+1 through k + Wait[k]). The dancer with the
 * highest total score at the end of the night wins the contest, so you want your
 * total score to be as high as possible.
 * Describe and analyze an efficient algorithm to compute the maximum
 * total score you can achieve. The input to your sweet algorithm is the pair of
 * arrays Score[1 .. n] and Wait[1 .. n].
 */
public final class BestScore {

    private BestScore() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    /**
     * N = scores.length
     * time: O(N)
     * space: O(N)
     */
    public static long findBestScore(int[] scores, int[] waitIntervals) {
        checkArgument(scores != null, "null 'scores' detected");
        checkArgument(waitIntervals != null, "null 'waitIntervals' detected");
        checkArgument(scores.length == waitIntervals.length, "scores.length != waitIntervals.length, %s != %s",
                      scores.length, waitIntervals.length);
        checkAllPositive(scores, "scores");
        checkAllPositive(waitIntervals, "waitIntervals");

        // store result as long, to prevent integer overflow errors
        long[] opt = new long[scores.length];
        opt[opt.length - 1] = scores[scores.length - 1];

        for (int i = scores.length - 2; i >= 0; --i) {

            int nextScoreIndex = i + waitIntervals[i] + 1;

            opt[i] = Math.max(
                    scores[i] + (nextScoreIndex < opt.length ? opt[nextScoreIndex] : 0), // take into account score[i]
                    opt[i + 1] // skip score[i]
            );
        }

        return opt[0];
    }

    private static void checkAllPositive(int[] arr, String paramName) {
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] < 0) {
                throw new IllegalArgumentException(String.format("Negative value for '%s' detected at index: %d",
                                                                 paramName, i));
            }
        }
    }
}
