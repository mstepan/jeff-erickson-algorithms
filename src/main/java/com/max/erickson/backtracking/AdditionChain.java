package com.max.erickson.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public final class AdditionChain {

    private AdditionChain() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Find shortest addition chain.
     */
    public static List<Integer> findChain(int value) {

        checkArgument(value > 0, "Negative or zero value passed: " + value);

        if (value == 1) {
            return List.of(1);
        }

        List<List<Integer>> res = findChainRec(value);

        assert !res.isEmpty() : "empty result detected";

        return res.get(0);
    }

    private static final Map<Integer, List<List<Integer>>> MEMORY = new HashMap<>();

    static {
        MEMORY.put(1, List.of(List.of(1)));
        MEMORY.put(2, List.of(List.of(1, 2)));
        MEMORY.put(3, List.of(List.of(1, 2, 3)));
    }

    private static List<List<Integer>> findChainRec(int value) {

        if (MEMORY.containsKey(value)) {
            return MEMORY.get(value);
        }

        List<List<Integer>> optimalSolutions = new ArrayList<>();

        int bestLength = Integer.MAX_VALUE;

        for (int first = 1; first <= value / 2; ++first) {

            int second = value - first;

            List<List<Integer>> firstHalf = findChainRec(first);
            List<List<Integer>> secondHalf = findChainRec(second);

            // there could be few optimal solutions for each value and we need to check pair-wise all
            for (List<Integer> firstSingleList : firstHalf) {
                for (List<Integer> secondSingleList : secondHalf) {

                    List<Integer> merged = merge(firstSingleList, secondSingleList, value);

                    if (merged.size() == bestLength) {
                        optimalSolutions.add(new ArrayList<>(merged));
                    }
                    else if (merged.size() < bestLength) {

                        bestLength = merged.size();

                        optimalSolutions.clear();
                        optimalSolutions.add(new ArrayList<>(merged));
                    }
                }
            }
        }

        MEMORY.put(value, optimalSolutions);

//        System.out.printf("FOUND for %d, optimal solutions: %d%n", value, optimalSolutions.size());

        return optimalSolutions;
    }

    private static List<Integer> merge(List<Integer> first, List<Integer> second, int lastValue) {
        List<Integer> res = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < first.size() || j < second.size()) {

            if (i == first.size()) {
                res.add(second.get(j));
                ++j;
            }
            else if (j == second.size()) {
                res.add(first.get(i));
                ++i;
            }
            else {
                int elem1 = first.get(i);
                int elem2 = second.get(j);

                if (elem1 == elem2) {
                    res.add(elem1);
                    ++i;
                    ++j;
                }
                else if (elem1 < elem2) {
                    res.add(elem1);
                    ++i;
                }
                else {
                    res.add(elem2);
                    ++j;
                }
            }
        }

        res.add(lastValue);
        return res;
    }
}
