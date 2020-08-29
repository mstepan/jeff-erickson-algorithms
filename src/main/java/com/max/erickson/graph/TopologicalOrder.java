package com.max.erickson.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public final class TopologicalOrder {

    private TopologicalOrder() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    /**
     * Exercise 4d. Use topological order.
     * <p>
     * time: O(V+E)
     * space: O(V)
     */
    public static Optional<List<String>> findTopologicalOrder(Graph graph) {

        Map<String, Integer> inDegree = calculateInDegree(graph);

        Queue<String> q = new ArrayDeque<>();

        for (String singleVertex : graph.vertexes()) {
            // all missed from 'inDegree' vertexes has 0 in degree
            if (!inDegree.containsKey(singleVertex)) {
                q.add(singleVertex);
            }
        }

        List<String> topologicalOrderRes = new ArrayList<>();
        while (!q.isEmpty()) {
            String vertex = q.poll();
            topologicalOrderRes.add(vertex);

            for (String dest : graph.getAdjacent(vertex)) {
                int destInDegree = inDegree.compute(dest, (key, value) -> value - 1);

                if (destInDegree == 0) {
                    inDegree.remove(dest);
                    q.add(dest);
                }
            }
        }

        // if inDegree.isEmpty - everything is OK, otherwise there is a cycle, so topological order is not possible
        return inDegree.isEmpty() ? Optional.of(topologicalOrderRes) : Optional.empty();
    }

    private static Map<String, Integer> calculateInDegree(Graph graph) {
        Map<String, Integer> inDegree = new HashMap<>();

        for (String singleVertex : graph.vertexes()) {
            for (String destVertex : graph.getAdjacent(singleVertex)) {
                inDegree.compute(destVertex, (key, value) -> value == null ? 1 : value + 1);
            }
        }

        return inDegree;
    }

}
