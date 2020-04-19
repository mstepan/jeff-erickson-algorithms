package com.max.erickson.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * <p>
     * N = rewards.length
     * <p>
     * time: O(N^3), we need to traverse N^2 solutions table and we spend O(N) for each cell.
     * space: O(N^2)
     */
    public static int maxTotalReward(int[][] rewards) {
        checkArgument(rewards != null, "null 'rewards' 2D array passed");
        checkIsMatrix(rewards);

        int snailsCount = rewards.length;

        // rows = count + 1, to mitigate IndexOutOfBoundsException for 'opt[k + 1][col]' call
        int[][] opt = new int[snailsCount + 1][snailsCount];

        // store best solution, just to check
        String[][] solution = new String[snailsCount + 1][snailsCount];

        for (int row = snailsCount - 2; row >= 0; --row) {

            for (int col = row + 1; col < snailsCount; ++col) {

                // skip current snail
                int maxReward = opt[row + 1][col];
                String bestSolution = solution[row + 1][col];

                // check pairing of current snail with all other snails
                for (int k = row + 1; k <= col; ++k) {

                    int possibleReward = rewards[row][k] + opt[row + 1][k - 1] + opt[k + 1][col];

                    if (possibleReward > maxReward) {
                        maxReward = possibleReward;
                        bestSolution = (row + " - " + k) +
                                emptyIfNull(solution[row + 1][k - 1]) + emptyIfNull(solution[k + 1][col]);
                    }
                }

                opt[row][col] = maxReward;
                solution[row][col] = bestSolution;
            }
        }

        LOG.info("solution: {}", solution[0][snailsCount - 1]);

        return opt[0][snailsCount - 1];
    }

    private static String emptyIfNull(String other) {
        return other == null ? "" : ", " + other;
    }

    private static void checkIsMatrix(int[][] matrix) {
        int size = matrix.length;

        for (int row = 0; row < matrix.length; ++row) {
            int[] singleRow = matrix[row];
            if (singleRow == null || singleRow.length != size) {
                String errorMsg = String.format("Incorrect matrix passed, the row number '%d' is null or of incorrect size, " +
                                                        "expected: %d, but found: %s",
                                                row, size, (singleRow == null ? "null" : singleRow.length));

                throw new IllegalStateException(errorMsg);
            }
        }
    }
}
