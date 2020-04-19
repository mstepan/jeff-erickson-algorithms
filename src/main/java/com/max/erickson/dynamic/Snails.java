package com.max.erickson.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise 36.
 * <p>
 * Every year, as part of its annual meeting, the Antarctican Snail Lovers of
 * Upper Glacierville hold a Round Table Mating Race.
 */
public final class Snails {

    private static final Logger LOG = LoggerFactory.getLogger(Snails.class);

    private Snails() {
        throw new IllegalStateException("Can't instantiate utility only class");
    }


    /**
     * Dynamic programming solution.
     */
    public static int maxTotalReward(int[][] rewards) {
        checkArgument(rewards != null, "null 'rewards' 2D array passed");

        int snailsCount = rewards.length;
        int finalState = (1 << snailsCount) - 1;

        Map<Integer, Integer> memoizationTable = new HashMap<>();

        int[] callsWithMemoization = {0};

        int res = findMaxReward(rewards, memoizationTable, 0, finalState, callsWithMemoization);

        LOG.info("callsWithMemoization: {}", callsWithMemoization[0]);

        return res;
    }

    private static int findMaxReward(int[][] rewards, Map<Integer, Integer> memoizationTable, int state, int finalState,
                                     int[] callsWithMemoization) {
        if (state == finalState) {
            return 0;
        }

        if (memoizationTable.containsKey(state)) {
            callsWithMemoization[0] += 1;
            return memoizationTable.get(state);
        }

        int i = findZeroIndex(0, state, rewards.length);

        int maxReward = 0;

        for (int j = findZeroIndex(i + 1, state, rewards.length); j != -1; j = findZeroIndex(j + 1, state, rewards.length)) {

            int newState = setBits(state, i, j);

            maxReward = Math.max(maxReward, rewards[i][j] +
                    findMaxReward(rewards, memoizationTable, newState, finalState, callsWithMemoization));
        }

        memoizationTable.put(state, maxReward);

        return maxReward;
    }

    private static int setBits(int state, int i, int j) {
        return state ^ (1 << i) ^ (1 << j);
    }

    private static int findZeroIndex(int from, int state, int bitsCount) {
        int mask = 1;
        for (int i = from; i < bitsCount; ++i) {
            if ((mask & state) == 0) {
                return i;
            }

            mask <<= 1;
        }

        return -1;
    }

}
