package com.max.erickson.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 5.7 Graph Reductions: Flood Fill.
 */
public class FloodFillMain {

    public static void main(String[] args) {

        int[][] board = {
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0}
        };

        print2DArray(board);

        fillBoardSegment(board, 4, 0, 2);

        System.out.println();
        print2DArray(board);

        System.out.println("Flood Fill completed...");
    }

    private static void print2DArray(int[][] arr) {
        for (int[] row : arr) {
            System.out.println(Arrays.toString(row));
        }
    }

    /**
     * time: O(N^2)
     * space: O(1), in-place algorithm.
     */
    static void fillBoardSegment(int[][] board, int row, int col, int colorToFill) {
        checkNotNull(board, "null 'board' passed");

        checkArgument(board.length > 0, "EMPTY board passed");
        checkArgument(board[0] != null, "board[0] is NULL");

        final int rows = board.length;
        final int cols = board[0].length;

        checkBoardRectangular(board);
        checkValidRowAndCol(row, col, rows, cols);

        final int initialColor = board[row][col];

        if (initialColor == colorToFill) {
            return;
        }

        Queue<Position> queue = new ArrayDeque<>();
        board[row][col] = colorToFill;
        queue.add(Position.of(row, col));

        // Use BFS here, because BFS usually more memory efficient for flood filling.
        while (!queue.isEmpty()) {

            Position cur = queue.poll();

            for (Position adj : adjacent(cur, rows, cols)) {

                if (board[adj.row][adj.col] == initialColor) {
                    board[adj.row][adj.col] = colorToFill;
                    queue.add(adj);
                }
            }
        }
    }

    private static final int[][] SUPPORTED_OFFSETS = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    private static List<Position> adjacent(Position cur, int rows, int cols) {
        List<Position> res = new ArrayList<>();

        for (int[] offset : SUPPORTED_OFFSETS) {
            int newRow = cur.row + offset[0];
            int newCol = cur.col + offset[1];

            if (isValidPosition(newRow, newCol, rows, cols)) {
                res.add(Position.of(newRow, newCol));
            }
        }

        return res;
    }

    private static boolean isValidPosition(int newRow, int newCol, int totalRows, int totalCols) {
        return newRow >= 0 && newRow < totalRows && newCol >= 0 && newCol < totalCols;
    }

    private static void checkBoardRectangular(int[][] board) {

        final int expectedRows = board[0].length;

        for (int i = 1; i < board.length; ++i) {
            checkArgument(board[i].length == expectedRows, "board not square or rectangular");
        }
    }

    private static void checkValidRowAndCol(int actualRow, int actualCol, int totalRows, int totalCols) {
        checkArgument(actualRow >= 0, "negative row detected: %s", actualRow);
        checkArgument(actualRow < totalRows, "row >= totalRows: %s > %s", actualRow, totalRows);

        checkArgument(actualCol >= 0, "negative col detected: %s", actualCol);
        checkArgument(actualCol < totalCols, "col >= totalCols: %s > %s", actualCol, totalCols);
    }

    private static class Position {
        final int row;
        final int col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        static Position of(int row, int col) {
            return new Position(row, col);
        }

        @Override
        public String toString() {
            return "row: " + row + ", col: " + col;
        }

    }


}
