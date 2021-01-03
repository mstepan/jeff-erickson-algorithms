package com.max.erickson.graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise: 11.
 * <p>
 * A number maze is an NxN grid of positive integers. A token starts in the
 * upper left corner; your goal is to move the token to the lower-right corner.
 * On each turn, you are allowed to move the token up, down, left, or right;
 * the distance you may move the token is determined by the number on its
 * current square. For example, if the token is on a square labeled 3, then you
 * may move the token three steps up, three steps down, three steps left, or
 * three steps right. However, you are never allowed to move the token off the
 * edge of the board.
 * Describe and analyze an efficient algorithm that either returns the
 * minimum number of moves required to solve a given number maze, or
 * correctly reports that the maze has no solution. For example, given the
 * number maze in Figure 5.14, your algorithm should return the integer 8.
 */
public final class NumberMaze {

    public static int findMinMovesCount(int[][] board) {
        checkArgument(board != null, "null 'board' detected");
        checkIsMatrix(board);

        int path = findMinPathLength(board);
        if (path < 0) {
            return -1;
        }

        // move count is equal to path length minus 1
        return path - 1;
    }


    private static int findMinPathLength(int[][] board) {

        final int n = board.length;

        final BoardPosition finalPos = BoardPosition.of(n - 1, n - 1);

        final Set<BoardPosition> used = new HashSet<>();

        Queue<PositionAndLength> queue = new ArrayDeque<>();
        queue.add(PositionAndLength.of(BoardPosition.of(0, 0), 1));

        while (!queue.isEmpty()) {
            PositionAndLength cur = queue.poll();

            if (cur.position.equals(finalPos)) {
                return cur.pathLength;
            }

            List<BoardPosition> candidates = findPossibleMoves(board, cur.position, used, n);

            for (BoardPosition nextPos : candidates) {
                queue.add(PositionAndLength.of(nextPos, cur.pathLength + 1));
                used.add(nextPos);
            }
        }

        return -1;
    }

    private static List<BoardPosition> findPossibleMoves(int[][] board, BoardPosition cur, Set<BoardPosition> used, int n) {

        int row = cur.row;
        int col = cur.col;

        int steps = board[row][col];

        return List.of(
                BoardPosition.of(row - steps, col),
                BoardPosition.of(row + steps, col),
                BoardPosition.of(row, col - steps),
                BoardPosition.of(row, col + steps)).
                stream().
                filter(pos -> withinBoard(board, pos)).
                filter(pos -> notUsed(used, pos)).
                collect(Collectors.toList());
    }

    private static boolean withinBoard(int[][] board, BoardPosition pos) {
        int row = pos.row;
        int col = pos.col;

        return (row >= 0 && row < board.length) && (col >= 0 && col < board.length);
    }

    private static boolean notUsed(Set<BoardPosition> used, BoardPosition pos) {
        return !used.contains(pos);
    }

    private static void checkIsMatrix(int[][] board) {
        assert board != null : "null 'board' detected";

        int n = board.length;

        for (int[] row : board) {
            if (row == null || row.length != n) {
                throw new IllegalArgumentException("'board' is not a proper matrix");
            }
        }
    }

    private static final class PositionAndLength {
        final BoardPosition position;
        final int pathLength;

        static PositionAndLength of(BoardPosition position, int pathLength) {
            return new PositionAndLength(position, pathLength);
        }

        PositionAndLength(BoardPosition position, int pathLength) {
            this.position = position;
            this.pathLength = pathLength;
        }
    }

    private static final class BoardPosition {
        final int row;
        final int col;

        static BoardPosition of(int row, int col) {
            return new BoardPosition(row, col);
        }

        BoardPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != BoardPosition.class) {
                return false;
            }
            BoardPosition other = (BoardPosition) obj;

            return row == other.row && col == other.col;
        }

        @Override
        public int hashCode() {
            return 31 * row + col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

}
