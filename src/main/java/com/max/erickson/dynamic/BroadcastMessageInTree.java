package com.max.erickson.dynamic;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Exercise-49.
 * <p>
 * Suppose we need to broadcast a message to all the nodes in a rooted tree.
 * Initially, only the root node knows the message. In a single round, any node
 * that knows the message can forward it to at most one of its children.
 * <p>
 * Design an algorithm to compute the minimum number of rounds required
 * to broadcast the message to all nodes in an arbitrary rooted tree.
 */
public final class BroadcastMessageInTree {

    private BroadcastMessageInTree() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    /**
     * Traverse the tree in post-order (all children then parent) and
     * calculate minimum number of trips using memoization technique.
     * <p>
     * N = tree nodes count
     * <p>
     * time: O(N*lgN)
     * space: O(N)
     */
    public static <U> int minTripsCount(TreeNode<U> tree) {
        checkArgument(tree != null, "null 'tree' parameter detected");
        return minTripsRec(tree, new HashMap<>());
    }

    private static <U> int minTripsRec(TreeNode<U> cur, Map<TreeNode<U>, Integer> optimal) {
        if (optimal.containsKey(cur)) {
            return optimal.get(cur);
        }

        // list should be sorted in DESC order (from biggest values to smallest)
        List<Integer> sortedChildrenTripsCount =
                cur.getChildren().stream().
                        map(node -> minTripsRec(node, optimal)).
                        sorted(Comparator.reverseOrder()).
                        collect(Collectors.toList());

        int leftTrips = 0;
        int res = 0;

        for (int curOpt : sortedChildrenTripsCount) {
            res += Math.max(1 + curOpt - leftTrips, 0);
            leftTrips = Math.max(leftTrips - 1, curOpt);
        }

        optimal.put(cur, res);
        return res;
    }

}
