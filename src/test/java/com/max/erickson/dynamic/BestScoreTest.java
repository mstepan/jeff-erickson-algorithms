package com.max.erickson.dynamic;

import org.junit.Test;

import static com.max.erickson.dynamic.BestScore.findBestScore;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BestScoreTest {

    @Test
    public void findBestScoreNormalCase() {
        assertThat(findBestScore(new int[]{2, 5, 2, 4, 2, 3, 1}, new int[]{1, 2, 1, 2, 2, 1, 1})).
                isEqualTo(8L);

        assertThat(findBestScore(new int[]{4, 5, 2, 4, 2, 3, 1}, new int[]{1, 2, 1, 2, 2, 1, 1})).
                isEqualTo(9L);
    }
}
