package com.max.erickson.graph;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NumberMazeTest {

    @Test
    public void findMinMovesCountNormalCase() {

        int[][] board = {
                {3, 5, 7, 4, 6},
                {5, 3, 1, 5, 3},
                {2, 8, 3, 1, 4},
                {4, 5, 7, 2, 3},
                {3, 1, 3, 2, 0}
        };

        int path = NumberMaze.findMinMovesCount(board);

        assertThat(path).isEqualTo(8);
    }

    @Test
    public void findMinMovesCountNoSolution() {

        int[][] board = {
                {3, 5, 7, 4, 6},
                {5, 3, 1, 5, 3},
                {2, 8, 3, 1, 4},
                {4, 5, 7, 2, 3},
                {3, 10, 3, 2, 0}
        };

        int path = NumberMaze.findMinMovesCount(board);

        assertThat(path).isEqualTo(-1);
    }

}
