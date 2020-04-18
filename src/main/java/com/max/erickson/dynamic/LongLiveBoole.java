package com.max.erickson.dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise 35.
 * <p>
 * After graduating from Sham-Poobanana University, you decide to interview
 * for a position at the Wall Street bank Long Live Boole. The managing
 * director of the bank, Eloob Egroeg, poses a ’solve-or-die’ problems to each
 * new employee, which they must solve within 24 hours. Those who fail to
 * solve the problem are fired immediately!
 * Entering the bank for the first time, you notice that the employee offices
 * are organized in a straight row, with a large T or F printed on the door of
 * each office. Furthermore, between each adjacent pair of offices, there is a
 * board marked by one of the symbols ^,_, or . When you ask about these
 * arcane symbols, Eloob confirms that T and F represent the boolean values
 * True and False, and the symbols on the boards represent the standard
 * boolean operators And, Or, and Xor. He also explains that these letters
 * and symbols describe whether certain combinations of employees can work
 * together successfully. At the start of any new project, Eloob hierarchically
 * clusters his employees by adding parentheses to the sequence of symbols, to
 * obtain an unambiguous boolean expression. The project is successful if this
 * parenthesized boolean expression evaluates to T.
 */
public final class LongLiveBoole {

    private static final char BOOLEAN_TRUE = 'T';
    private static final char BOOLEAN_FALSE = 'F';

    // conjunction operator
    private static final char MUL = '*';

    // disjunction
    private static final char ADD = '+';

    // xor
    private static final char XOR = '^';

    private static final char[] OPERATORS = {MUL, ADD, XOR};


    public static Optional<String> findTrueExpression(String exp) {
        checkArgument(exp != null, "null 'exp' passed");
        return findTrueExpressionImpl(exp);
    }

    public static boolean canBeTrue(String expStr) {
        return findTrueExpressionImpl(expStr).isPresent();
    }

    /**
     * Dynamic programming solution.
     * N = exp.length
     * time: O(N^3), we need to spend N time for each cell of N^2 table.
     * space: O(N^2)
     */
    private static Optional<String> findTrueExpressionImpl(String expStr) {

        checkArgument(expStr != null, "null 'exp' string passed");

        final char[] exp = expStr.toCharArray();
        checkExpressionValid(exp);

        final int size = exp.length;

        final boolean[][] isTrue = new boolean[size][size];
        final boolean[][] isFalse = new boolean[size][size];

        final Map<State, String> solution = new HashMap<>();

        // fill both tables simultaneously from bottom row and up
        for (int row = size - 1; row >= 0; row -= 2) {
            for (int col = row; col < size; col += 2) {

                if (row == col) {
                    isTrue[row][col] = (BOOLEAN_TRUE == exp[row]);
                    isFalse[row][col] = (BOOLEAN_FALSE == exp[row]);
                    solution.put(new State(row, col, BOOLEAN_TRUE == exp[row]), String.valueOf(exp[row]));
                }
                else {
                    for (int k = row + 1; k < col; k += 2) {

                        assert isValidOperator(exp[k]) : "Not a valid operator";

                        switch (exp[k]) {
                            case MUL -> updateMul(isTrue, isFalse, solution, row, k, col);
                            case ADD -> updateAdd(isTrue, isFalse, solution, row, k, col);
                            case XOR -> updateXor(isTrue, isFalse, solution, row, k, col);
                        }
                    }
                }
            }
        }

        return Optional.ofNullable(solution.get(new State(0, size - 1, true)));
    }

    private static final class State {
        final int i;
        final int j;
        final boolean side;

        public State(int i, int j, boolean side) {
            this.i = i;
            this.j = j;
            this.side = side;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State that = (State) obj;

            if (i != that.i) {
                return false;
            }
            if (j != that.j) {
                return false;
            }
            return side == that.side;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j, side);
        }

        @Override
        public String toString() {
            return i + ", " + j + ", " + side;
        }
    }

    private static void updateMul(boolean[][] isTrue, boolean[][] isFalse, Map<State, String> solution, int i, int k, int j) {
        // update table for TRUE
        if (isTrue[i][k - 1] && isTrue[k + 1][j]) {
            isTrue[i][j] = true;
            updateSolution(solution, true, MUL, i, k, j, true, true);
        }

        // update table for FALSE
        if (isFalse[i][k - 1] || isFalse[k + 1][j]) {
            isFalse[i][j] = true;
            updateSolution(solution, false, MUL, i, k, j, !isFalse[i][k - 1], !isFalse[k + 1][j]);
        }
    }

    private static void updateAdd(boolean[][] isTrue, boolean[][] isFalse, Map<State, String> solution, int i, int k, int j) {

        // update table for TRUE
        if (isTrue[i][k - 1] || isTrue[k + 1][j]) {
            isTrue[i][j] = true;
            updateSolution(solution, true, ADD, i, k, j, isTrue[i][k - 1], isTrue[k + 1][j]);
        }

        // update table for FALSE
        if (isFalse[i][k - 1] && isFalse[k + 1][j]) {
            isFalse[i][j] = true;
            updateSolution(solution, false, ADD, i, k, j, false, false);
        }
    }

    private static void updateSolution(Map<State, String> solution, boolean curSideValue, char operator, int i, int k, int j,
                                       boolean leftSideValue, boolean rightSideValue) {

        String leftSide = solution.get(new State(i, k - 1, leftSideValue));
        String rightSide = solution.get(new State(k + 1, j, rightSideValue));

        solution.put(new State(i, j, curSideValue), String.format("(%s %s %s)", leftSide, operator, rightSide));

    }

    private static void updateXor(boolean[][] isTrue, boolean[][] isFalse, Map<State, String> solution, int i, int k, int j) {

        // update table for TRUE
        if (isTrue[i][k - 1] && isFalse[k + 1][j]) {
            isTrue[i][j] = true;
            updateSolution(solution, true, XOR, i, k, j, true, false);
        }
        if (isFalse[i][k - 1] && isTrue[k + 1][j]) {
            isTrue[i][j] = true;
            updateSolution(solution, true, XOR, i, k, j, false, true);
        }

        // update table for FALSE
        if (isFalse[i][k - 1] && isFalse[k + 1][j]) {
            isFalse[i][j] = true;
            updateSolution(solution, false, XOR, i, k, j, false, false);
        }
        if (isTrue[i][k - 1] && isTrue[k + 1][j]) {
            isFalse[i][j] = true;
            updateSolution(solution, false, XOR, i, k, j, true, true);
        }
    }

    private static void checkExpressionValid(char[] exp) {
        for (int i = 0; i < exp.length; i += 2) {
            if (!isValidBooleanValue(exp[i])) {
                throw new IllegalArgumentException(
                        String.format("Bad expression, value at index %d should be '%s' or '%s', but found '%s'",
                                      i, BOOLEAN_TRUE, BOOLEAN_FALSE, exp[i]));
            }
        }

        for (int i = 1; i < exp.length; i += 2) {
            if (!isValidOperator(exp[i])) {
                throw new IllegalArgumentException(
                        String.format("Bad expression, value at index %d should be a valid operator %s, but found '%s'",
                                      i, Arrays.toString(OPERATORS), exp[i]));
            }
        }
    }

    private static boolean isValidBooleanValue(char value) {
        return value == BOOLEAN_TRUE || value == BOOLEAN_FALSE;
    }

    private static boolean isValidOperator(char actualOperator) {

        for (char validOperator : OPERATORS) {
            if (validOperator == actualOperator) {
                return true;
            }
        }

        return false;
    }
}
