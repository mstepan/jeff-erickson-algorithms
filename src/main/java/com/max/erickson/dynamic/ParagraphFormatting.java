package com.max.erickson.dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exercise 11. Paragraph formatting with minimum slop.
 */
public class ParagraphFormatting {

    /**
     * N = words count
     * <p>
     * time: O(N^2)
     * space: O(N^2)
     *
     * @param words - all words to be formatted
     * @param L     - maximum allowed paragraph length
     * @return lines of paragraph with minimum slop.
     */
    public static List<String> optimalFormatting(String[] words, int L) {

        final int size = words.length;

        int[] opt = new int[size];
        int[] optWords = new int[size];

        for (int i = 0; i < size; ++i) {

            int curSize = -1;

            int minSlope = Integer.MAX_VALUE;
            int minWordsCount = 0;

            for (int j = i; j >= 0; --j) {
                curSize += words[j].length() + 1;

                if (curSize > L) {
                    break;
                }

                int curSlope = slope(curSize, L, j, i, i == size - 1) + (j == 0 ? 0 : opt[j - 1]);

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
    private static int slope(int curSize, int L, int from, int to, boolean lastLine) {

        if (lastLine) {
            return 0;
        }

        int leftSpaces = L - curSize;
        return leftSpaces * leftSpaces * leftSpaces;
    }

    public static void main(String[] args) {



        String[] words = ("You and your eight-year-old nephew Elmo decide to play a simple card " +
                "game. At the beginning of the game, the cards are dealt face up in a long " +
                "row. Each card is worth a different number of points. After all the cards are " +
                "dealt, you and Elmo take turns removing either the leftmost or rightmost " +
                "card from the row, until all the cards are gone. At each turn, you can decide " +
                "which of the two cards to take. The winner of the game is the player that " +
                "has collected the most points when the game ends.")
                .split("\\s+");


        int L = 77;

        for (String singleLine : optimalFormatting(words, L)) {
            System.out.printf("%s ==> %d%n", singleLine, singleLine.length());
        }

        System.out.println("===================================");

        String[] expected = ("You and your eight-year-old nephew Elmo decide to play a simple card\n" +
                "game. At the beginning of the game, the cards are dealt face up in a long\n" +
                "row. Each card is worth a different number of points. After all the cards are\n" +
                "dealt, you and Elmo take turns removing either the leftmost or rightmost\n" +
                "card from the row, until all the cards are gone. At each turn, you can decide\n" +
                "which of the two cards to take. The winner of the game is the player that\n" +
                "has collected the most points when the game ends.").split("\n");

        for (String singleLine : expected) {
            System.out.printf("%s ==> %d%n", singleLine, singleLine.length());
        }

        System.out.println(cube(3) + cube(3) + cube(2) + cube(4) + cube(6));
        System.out.println(cube(9) + cube(4) + cube(5) + cube(4));

    }

    private static int cube(int value){
        return value * value * value;
    }

}
