package com.max.erickson.dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exercise 11. Paragraph formatting with minimum slop.
 * <p>
 * Suppose we want to typeset a paragraph of text onto a piece of paper (or if
 * you insist, a computer screen). The text consists of a sequence of n words,
 * where the ith word has length l[i]. We want to break the paragraph into
 * several lines of total length exactly L. Describe a dynamic programming
 * algorithm to print the paragraph with minimum slop.
 */
public class ParagraphFormatting {

    /**
     * N = words count
     * <p>
     * time: O(N^2)
     * space: O(N^2)
     *
     * @param words           - all words to be formatted
     * @param paragraphLength - maximum allowed paragraph length
     * @return lines of paragraph with minimum slop.
     */
    public static List<String> optimalFormatting(String[] words, int paragraphLength) {

        final int size = words.length;

        int[] opt = new int[size];
        int[] optWords = new int[size];

        for (int i = 0; i < size; ++i) {

            int curSize = -1;

            int minSlope = Integer.MAX_VALUE;
            int minWordsCount = 0;

            for (int j = i; j >= 0; --j) {
                curSize += words[j].length() + 1;

                if (curSize > paragraphLength) {
                    break;
                }

                boolean isLastLine = i == size - 1;
                int curSlope = slope(curSize, paragraphLength, j, i, isLastLine) + (j == 0 ? 0 : opt[j - 1]);

                if (curSlope < minSlope) {
                    minSlope = curSlope;
                    minWordsCount = i - j + 1;
                }
            }

            opt[i] = minSlope;
            optWords[i] = minWordsCount;
        }

        return buildSolution(words, optWords);
    }

    private static List<String> buildSolution(String[] words, int[] optWords) {

        List<String> paragraph = new ArrayList<>();

        int index = words.length - 1;

        while (index >= 0) {

            int count = optWords[index];

            int from = index - count + 1;
            int to = index;

            paragraph.add(buildLine(words, from, to));

            index -= count;
        }

        Collections.reverse(paragraph);

        return paragraph;
    }

    private static String buildLine(String[] words, int from, int to) {

        StringBuilder buf = new StringBuilder();

        buf.append(words[from]);

        for (int i = from + 1; i <= to; ++i) {
            buf.append(" ").append(words[i]);
        }

        return buf.toString();
    }

    /**
     * Calculate slope for a subarray.
     * Specifically, if a line contains words i through j, then the slop of that line is
     * defined to be slope = (L - j + i + totalWordsLength)^3
     */
    private static int slope(int curSize, int paragraphLength, int from, int to, boolean lastLine) {

        if (lastLine) {
            return 0;
        }

        int leftSpaces = paragraphLength - curSize;
        return leftSpaces * leftSpaces * leftSpaces;
    }

    public static void main(String[] args) {

        String[] words = """
                You and your eight-year-old nephew Elmo decide to play a simple card
                game. At the beginning of the game, the cards are dealt face up in a long
                row. Each card is worth a different number of points. After all the cards are
                dealt, you and Elmo take turns removing either the leftmost or rightmost
                card from the row, until all the cards are gone. At each turn, you can decide
                which of the two cards to take. The winner of the game is the player that
                has collected the most points when the game ends.
                """.split("\\s+");

        final int paragraphLength = 77;

        List<String> actualFormatting = optimalFormatting(words, paragraphLength);

        for (String singleLine : actualFormatting) {
            System.out.printf("%s ==> %d%n", singleLine, singleLine.length());
        }

        System.out.println("===================================");
        System.out.printf("actual score: %d%n", calculateScore(actualFormatting, paragraphLength));
        System.out.println("===================================");

        String[] expected = """
                You and your eight-year-old nephew Elmo decide to play a simple card
                game. At the beginning of the game, the cards are dealt face up in a long
                row. Each card is worth a different number of points. After all the cards are
                dealt, you and Elmo take turns removing either the leftmost or rightmost
                card from the row, until all the cards are gone. At each turn, you can decide
                which of the two cards to take. The winner of the game is the player that
                has collected the most points when the game ends.
                """.split("\n");

        for (String singleLine : expected) {
            System.out.printf("%s ==> %d%n", singleLine, singleLine.length());
        }

        System.out.println("===================================");
        System.out.printf("expected score: %d%n", calculateScore(expected, paragraphLength));
        System.out.println("===================================");
    }

    private static int calculateScore(List<String> lines, int paragraphLength) {
        return calculateScore(lines.toArray(new String[]{}), paragraphLength);
    }

    private static int calculateScore(String[] lines, int paragraphLength) {

        int score = 0;
        for (int i = 0; i < lines.length - 1; ++i) {
            score += cube(paragraphLength - lines[i].length());
        }
        return score;
    }

    private static int cube(int value) {
        return value * value * value;
    }

}
