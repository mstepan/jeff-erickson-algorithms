package com.max.erickson.dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Amount change problem solved using dynamic & greedy approach (greedy is not always optimal).
 */
public final class CoinChange {

    private CoinChange() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Change money using backtracking recursive algorithm.
     * <p>
     * N = coins.length
     * M = amount
     * <p>
     * time: O(2^N)
     * space: O(M)
     */
    public static Optional<int[]> changeMoneyBacktracking(int amount, int[] coins) {

        checkPreconditions(amount, coins);

        List<Integer> bestResult = new ArrayList<>();
        changeMoneyRec(coins, coins.length - 1, amount, new ArrayList<>(), bestResult);

        return bestResult.isEmpty() ? Optional.empty() : Optional.of(toSortedArray(bestResult));
    }

    private static void changeMoneyRec(int[] coins, int i, int amount, List<Integer> curSolution, List<Integer> bestResult) {

        if (amount == 0) {
            if (bestResult.isEmpty() || curSolution.size() < bestResult.size()) {
                bestResult.clear();
                bestResult.addAll(curSolution);
            }
            return;
        }

        if (i < 0) {
            return;
        }

        if (coins[i] > amount) {
            // skip coins[i] value
            changeMoneyRec(coins, i - 1, amount, curSolution, bestResult);
            return;
        }

        // skip coins[i] value
        changeMoneyRec(coins, i - 1, amount, curSolution, bestResult);

        // check with coins[i] value
        curSolution.add(coins[i]);
        changeMoneyRec(coins, i, amount - coins[i], curSolution, bestResult);
        curSolution.remove(curSolution.size() - 1);
    }

    /**
     * Change amount using greedy approach. Not always produce an optimal solution.
     * <p>
     * N = coins.length
     * M = amount
     * <p>
     * time: N*lgN - sort coins, M - find solution for amount, so O(M * N*lgN)
     * space: up to O(M)
     */
    public static Optional<int[]> changeMoneyGreedy(int amount, int[] coins) {
        checkPreconditions(amount, coins);

        Arrays.sort(coins);

        int index = coins.length - 1;
        int money = amount;

        List<Integer> res = new ArrayList<>();

        while (money != 0 && index >= 0) {
            if (coins[index] > money) {
                --index;
            }
            else {
                res.add(coins[index]);
                money -= coins[index];
            }
        }

        if (money != 0) {
            return Optional.empty();
        }

        return Optional.of(toSortedArray(res));
    }

    /**
     * Change amount using dynamic programming approach. Always produce an optimal solution.
     * <p>
     * N = coins.length
     * M = amount
     * <p>
     * time: O(M*N)
     * space: O(M*N), can be reduced to O(M)
     */
    public static Optional<int[]> changeMoney(int amount, int[] coins) {

        checkPreconditions(amount, coins);

        if (amount == 0) {
            return Optional.of(new int[]{});
        }

        int[][] opt = new int[coins.length + 1][amount + 1];

        final int rows = opt.length;
        final int cols = opt[0].length;

        for (int col = 1; col < cols; ++col) {
            opt[0][col] = Integer.MAX_VALUE;
        }

        if (opt[rows - 1][cols - 1] == Integer.MAX_VALUE) {
            return Optional.empty();
        }

        for (int row = 1; row < rows; ++row) {
            for (int col = 1; col < cols; ++col) {
                final int cur = coins[row - 1];
                final int money = col;

                opt[row][col] = Math.min(
                        opt[row - 1][money], // without current element
                        (cur <= money) ? 1 + opt[row][money - cur] : Integer.MAX_VALUE // with current element if cur <= money
                );
            }
        }

        if (opt[rows - 1][cols - 1] == Integer.MAX_VALUE) {
            return Optional.empty();
        }

        return Optional.of(rebuildSolution(opt, coins));
    }

    private static void checkPreconditions(int amount, int[] coins) {
        checkArgument(amount >= 0, "Negative 'amount' passed: " + amount);
        checkArgument(coins != null, "null 'coins' array passed");
    }

    private static int[] rebuildSolution(int[][] opt, int[] coins) {

        assert opt != null : "null 'opt' detected";
        assert coins != null : "null 'coins' detected";

        int row = opt.length - 1;
        int col = opt[row].length - 1;

        List<Integer> res = new ArrayList<>();

        while (row != 0 && col != 0) {

            int curCoin = coins[row - 1];
            int money = col;

            if (opt[row - 1][col] == opt[row][col]) {
                // skip current element
                --row;
            }
            else {
                int newCol = money - curCoin;

                if (opt[row][newCol] + 1 == opt[row][col]) {
                    // use current element
                    res.add(curCoin);
                }

                col = newCol;
            }
        }

        return toSortedArray(res);
    }

    private static int[] toSortedArray(List<Integer> data) {
        int[] res = new int[data.size()];

        for (int i = 0; i < res.length; ++i) {
            res[i] = data.get(i);
        }

        Arrays.sort(res);

        return res;
    }

}
