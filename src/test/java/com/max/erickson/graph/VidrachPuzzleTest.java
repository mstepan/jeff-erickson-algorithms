package com.max.erickson.graph;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class VidrachPuzzleTest {

    @Test
    public void findMinMovesSolutionNormalCase() {

        final int[][] board = {
                {1, 2, 4, 3},
                {3, 4, 1, 2},
                {3, 1, 2, 3},
                {2, 3, 1, 2}
        };

        int minMoves = VidrachPuzzle.findMinMovesSolution(board);

        assertThat(minMoves).isEqualTo(5);
    }
}
