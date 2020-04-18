package com.max.erickson.dynamic;

import java.util.Arrays;

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


    public static boolean canBeTrue(String exp) {
        checkArgument(exp != null, "null 'exp' passed");
        return canBeTrue(exp.toCharArray());
    }

    public static boolean canBeTrue(char[] exp) {
        checkArgument(exp != null, "null 'exp' passed");
        checkExpressionValid(exp);

        final int size = exp.length;

        boolean[][] isTrue = new boolean[size][size];
        boolean[][] isFalse = new boolean[size][size];

        // fill both tables simultaneously from bottom row and up
        for (int row = size - 1; row >= 0; row -= 2) {
            for (int col = row; col < size; col += 2) {

                if (row == col) {
                    isTrue[row][col] = (BOOLEAN_TRUE == exp[row]);
                    isFalse[row][col] = (BOOLEAN_FALSE == exp[row]);
                }
                else {

                    for (int k = row + 1; k < col; k += 2) {

                        assert isValidOperator(exp[k]) : "Not a valid operator";

                        switch (exp[k]) {
                            case MUL -> updateMul(isTrue, isFalse, row, k, col);
                            case ADD -> updateAdd(isTrue, isFalse, row, k, col);
                            case XOR -> updateXor(isTrue, isFalse, row, k, col);
                        }
                    }
                }
            }
        }

        return isTrue[0][isTrue.length - 1];
    }

    private static void updateMul(boolean[][] isTrue, boolean[][] isFalse, int i, int k, int j) {
        // update table for TRUE
        if (isTrue[i][k - 1] && isTrue[k + 1][j]) {
            isTrue[i][j] = true;
        }

        // update table for FALSE
        if (isFalse[i][k - 1] || isFalse[k + 1][j]) {
            isFalse[i][j] = true;
        }
    }

    private static void updateAdd(boolean[][] isTrue, boolean[][] isFalse, int i, int k, int j) {

        // update table for TRUE
        if (isTrue[i][k - 1] || isTrue[k + 1][j]) {
            isTrue[i][j] = true;
        }

        // update table for FALSE
        if (isFalse[i][k - 1] && isFalse[k + 1][j]) {
            isFalse[i][j] = true;
        }
    }

    private static void updateXor(boolean[][] isTrue, boolean[][] isFalse, int i, int k, int j) {

        // update table for TRUE
        if ((isTrue[i][k - 1] && isFalse[k + 1][j]) || (isFalse[i][k - 1] && isTrue[k + 1][j])) {
            isTrue[i][j] = true;
        }

        // update table for FALSE
        if ((isFalse[i][k - 1] && isFalse[k + 1][j]) || (isTrue[i][k - 1] && isTrue[k + 1][j])) {
            isFalse[i][j] = true;
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
