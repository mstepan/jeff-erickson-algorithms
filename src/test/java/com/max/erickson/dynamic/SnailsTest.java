package com.max.erickson.dynamic;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SnailsTest {

    @Test
    public void maxTotalRewardNormalCase() {
        int[][] rewards = {
                {0, 3, 2, 4, 1, 4},
                {3, 0, 3, 2, 2, 1},
                {2, 3, 0, 5, 3, 6},
                {4, 2, 5, 0, 2, 3},
                {1, 2, 3, 2, 0, 5},
                {4, 1, 6, 3, 5, 0}
        };

        assertThat(Snails.maxTotalReward(rewards)).isEqualTo(12);
    }
}
