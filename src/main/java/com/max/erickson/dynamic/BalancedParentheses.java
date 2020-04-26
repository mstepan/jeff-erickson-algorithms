package com.max.erickson.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise 42.
 * (b) Describe and analyze an algorithm to compute the length of a longest
 * balanced subsequence of a given string of parentheses and brackets.
 */
public class BalancedParentheses {

    private static final Logger LOG = LoggerFactory.getLogger(BalancedParentheses.class);

    enum Parenthesis {
        ROUND('(', ')'),
        SQUARE('[', ']');

        final char open;
        final char closed;

        Parenthesis(char open, char closed) {
            this.open = open;
            this.closed = closed;
        }

        Stream<String> asStringStream() {
            return Stream.of(String.valueOf(open), String.valueOf(closed));
        }
    }

    private static final Set<Character> OPEN_PARENTHESIS =
            Arrays.stream(Parenthesis.values()).map(enumValue -> enumValue.open).collect(Collectors.toSet());

    private static final Set<Character> VALID_PARENTHESIS = Arrays.stream(Parenthesis.values()).
            flatMap(enumValue -> Stream.of(enumValue.open, enumValue.closed)).collect(Collectors.toSet());

    private static final String EXPECTED_CHARACTERS_MESSAGE =
            Arrays.stream(Parenthesis.values()).flatMap(Parenthesis::asStringStream).
                    reduce(null, (acc, value) -> acc == null ? value : acc + ", " + value);

    private BalancedParentheses() {
        throw new IllegalStateException("Can't instantiate utility-only class");
    }

    /**
     * dynamic programming solution.
     * time: O(N^3)
     * space: O(N^2)
     */
    public static int longestBalanced(String str) {
        checkArgument(str != null, "null 'str' passed");
        checkValidParenthesis(str);

        final char[] arr = str.toCharArray();

        final int[][] longest = new int[arr.length][arr.length];

        for (int i = arr.length - 2; i >= 0; --i) {
            for (int j = i + 1; j < arr.length; ++j) {

                // skip character at 'i'
                int maxSoFar = longest[i + 1][j];

                if (isOpen(arr[i])) {

                    // parenthesis at 'i' and 'j' match
                    if (isMatched(arr[i], arr[j])) {
                        maxSoFar = Math.max(maxSoFar, 1 + longest[i + 1][j - 1]);
                    }

                    char openCh = arr[i];

                    for (int k = i + 1; k < j; ++k) {
                        if (isMatched(openCh, arr[k])) {
                            maxSoFar = Math.max(maxSoFar, 1 + longest[i + 1][k - 1] + longest[k + 1][j]);
                        }
                    }
                }

                longest[i][j] = maxSoFar;
            }
        }

        return 2 * longest[0][arr.length - 1];
    }

    private static boolean isOpen(char ch) {
        return OPEN_PARENTHESIS.contains(ch);
    }

    private static boolean isMatched(char open, char closed) {
        return Arrays.stream(Parenthesis.values()).filter(enumValue -> enumValue.open == open).
                findAny().
                orElseThrow().
                closed == closed;
    }


    private static String[][] emptyStrings(int size) {
        final String[][] solution = new String[size][size];

        for (String[] row : solution) {
            Arrays.fill(row, "");
        }
        return solution;
    }

    /**
     * Check if string contains only allowed parenthesis characters.
     * time: O(N)
     * space: O(1)
     */
    private static void checkValidParenthesis(String str) {
        assert str != null : "null 'str' detected";

        for (int i = 0; i < str.length(); ++i) {
            if (!VALID_PARENTHESIS.contains(str.charAt(i))) {
                String errorMsg = String.format("Invalid parenthesis string '%s' at index: %d, expected characters: %s",
                                                str, i, EXPECTED_CHARACTERS_MESSAGE);

                throw new IllegalArgumentException(errorMsg);
            }
        }
    }
}
