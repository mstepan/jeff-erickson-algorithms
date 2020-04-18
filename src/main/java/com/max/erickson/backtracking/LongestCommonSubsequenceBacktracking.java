package com.max.erickson.backtracking;

import static com.google.common.base.Preconditions.checkArgument;

/*
Find longest common subsequence for 2 strings using backtracking technique.

time: O(2^n)
space: O(N)


Java flight recorder JVM options:

-XX:StartFlightRecording=settings=profile,dumponexit=true,filename=app-java13-recording.jfr
-XX:FlightRecorderOptions=samplethreads=true
-XX:+UnlockDiagnosticVMOptions
-XX:+DebugNonSafepoints
-ea
*/
public final class LongestCommonSubsequenceBacktracking {

    public static String findLongestCommonSubsequence(String first, String second) {

        checkArgument(first != null, "null string passed as 'first' parameter");
        checkArgument(second != null, "null string passed as 'second' parameter");

        return findLongestCommonSubsequence(first.toCharArray(), second.toCharArray());
    }

    private static String findLongestCommonSubsequence(char[] first, char[] second) {

        assert first != null : "null 'first' array";
        assert second != null : "null 'second' array";

        MutableCharArray largest = new MutableCharArray(first.length);
        MutableCharArray curSolution = new MutableCharArray(first.length);

        findLongestCommonSubseq(first, 0, second, 0, curSolution, largest);

        return largest.toString();
    }

    private static void findLongestCommonSubseq(char[] first, int i, char[] second, int j, MutableCharArray cur,
                                                MutableCharArray largest) {

        assert i <= first.length && j <= second.length;

        if (i == first.length || j == second.length) {

            if (cur.size() > largest.size()) {
                largest.replaceAll(cur);
            }

            return;
        }

        char ch1 = first[i];
        char ch2 = second[j];

        if (ch1 == ch2) {
            cur.addLast(ch1);
            findLongestCommonSubseq(first, i + 1, second, j + 1, cur, largest);
            cur.removeLast();
        }
        else {
            findLongestCommonSubseq(first, i + 1, second, j, cur, largest);
            findLongestCommonSubseq(first, i, second, j + 1, cur, largest);
        }
    }

    private static final class MutableCharArray {

        final char[] arr;
        int index;

        public MutableCharArray(int length) {
            this.arr = new char[length];
            this.index = 0;
        }

        private void addLast(char ch) {
            arr[index] = ch;
            ++index;
        }

        private void removeLast() {
            --index;
            arr[index] = 0;
        }

        private void replaceAll(MutableCharArray seq) {
            System.arraycopy(seq.arr, 0, arr, 0, seq.arr.length);
            index = seq.index;
        }

        private int size() {
            return index;
        }

        @Override
        public String toString() {
            return new String(arr, 0, index);
        }
    }

}
