package com.max.erickson.backtracking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public final class LongestBitonicSubSequenceBacktracking {

    private LongestBitonicSubSequenceBacktracking() {
        throw new IllegalStateException("Can't instantiate utility only class");
    }

    /**
     * time: O(2^N)
     * space: O(N)
     * Find longest bitonic subsequence using backtracking technique.
     */
    public static List<Integer> find(int[] arr) {

        Deque<Integer> longest = new ArrayDeque<>();

        findRec(arr, 0, Integer.MIN_VALUE, false, new ArrayDeque<>(), longest);

        return new ArrayList<>(longest);
    }

    private static void findRec(int[] arr, int index, int last, boolean changed, Deque<Integer> res, Deque<Integer> longest) {

        if (index == arr.length) {
            if (res.size() > longest.size()) {
                longest.clear();
                longest.addAll(res);
            }
            return;
        }

        int cur = arr[index];

        // skip cur element
        if (cur == last) {
            findRec(arr, index + 1, last, changed, res, longest);
            return;
        }

        if (cur > last) {
            if (changed) {
                // skip cur
                findRec(arr, index + 1, last, changed, res, longest);
                return;
            }

            // check with cur
            res.add(cur);
            findRec(arr, index + 1, cur, changed, res, longest);

            // check without cur
            res.removeLast();
            findRec(arr, index + 1, last, changed, res, longest);
        }
        else {
            // cur < last

            // subsequence going down
            if (changed) {
                // check with cur
                res.add(cur);
                findRec(arr, index + 1, cur, changed, res, longest);

                // check without cur
                res.removeLast();
                findRec(arr, index + 1, last, changed, res, longest);
            }
            // subsequence going up
            else {
                // check with cur
                res.add(cur);
                findRec(arr, index + 1, cur, true, res, longest);

                // check without cur
                res.removeLast();
                findRec(arr, index + 1, last, changed, res, longest);

            }
        }
    }

    /**
     * Find longest bitonic subsequence by enumerating all possible subsets from 'arr' and
     * checking for bitonic property.
     * time: O(2^n)
     * space: O(N)
     */
    public static List<Integer> findBruteforce(int[] arr) {

        checkArgument(arr != null, "null 'arr' parameter passed");
        checkArgument(arr.length <= 30, "arr.length should be less or equals to 30, but found " + arr.length);

        int lastMask = 1 << arr.length;

        List<Integer> longest = new ArrayList<>();

        for (int i = 0; i < lastMask; ++i) {

            List<Integer> cur = extractSubset(arr, i);

            if (cur.size() > longest.size() && isBitonicCollection(cur)) {
                longest = cur;
            }
        }

        return longest;
    }

    private static List<Integer> extractSubset(int[] arr, int mask) {

        List<Integer> res = new ArrayList<>();

        int curMask = mask;

        for (int i = 0; i < arr.length && curMask != 0; ++i, curMask >>= 1) {
            if ((curMask & 1) != 0) {
                res.add(arr[i]);
            }
        }

        return res;
    }

    private static boolean isBitonicCollection(List<Integer> list) {

        boolean changed = false;

        for (int i = 1; i < list.size(); ++i) {

            int prev = list.get(i - 1);
            int cur = list.get(i);

            if (cur == prev) {
                return false;
            }

            if (prev > cur) {
                changed = true;
            }
            else {
                // prev < cur
                if (changed) {
                    return false;
                }
            }
        }

        return true;
    }

}
