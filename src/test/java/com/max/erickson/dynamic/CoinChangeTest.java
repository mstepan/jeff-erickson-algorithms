package com.max.erickson.dynamic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoinChangeTest {

    private static final Logger LOG = LoggerFactory.getLogger(CoinChangeTest.class);

    private static final int[] EMPTY = {};

    @Test
    public void changeMoney() {

        int[] coins = {1, 4, 7, 13, 28, 52, 91, 365};

        for (int amount = 416; amount <= 420; ++amount) {
            int[] dynamicRes = CoinChange.changeMoney(amount, coins).orElse(EMPTY);
            int[] greedyRes = CoinChange.changeMoneyGreedy(amount, coins).orElse(EMPTY);
            int[] backtrackingRes = CoinChange.changeMoneyBacktracking(amount, coins).orElse(EMPTY);

            assertThat(sum(dynamicRes)).isEqualTo(sum(greedyRes));
            assertThat(sum(dynamicRes)).isEqualTo(sum(backtrackingRes));

            assertThat(dynamicRes.length).isLessThanOrEqualTo(greedyRes.length);
            assertThat(dynamicRes.length).isEqualTo(backtrackingRes.length);

            if (dynamicRes.length < greedyRes.length) {
                LOG.info("greedy failed for amount = " + amount);
                LOG.info("dynamic:      " + Arrays.toString(dynamicRes));
                LOG.info("greedy:       " + Arrays.toString(greedyRes));
                LOG.info("backtracking: " + Arrays.toString(backtrackingRes));
            }
        }
    }

    private static int sum(int[] arr) {
        assert arr != null;
        return Arrays.stream(arr).sum();
    }
}
