package com.max.erickson.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

public final class TopologicalSorting {

    private TopologicalSorting() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    /**
     * Classic topological sorting for directed graph.
     * If graph has cycle, return empty collection as a result.
     * <p>
     * time: O(V+E)
     * space: O(V)
     */
    public static List<String> topologicalOrder(Graph graph) {
        checkArgument(graph != null, "NULL 'graph' reference detected");
        checkArgument(graph.isOrdered(), "Can't execute topological sorting for UNDIRECTED graph.");

        List<String> allVertices = graph.vertexes();

        Map<String, Integer> incomeCntPerVertex = new HashMap<>();

        for (Graph.Edge singleEdge : graph.edges()) {
            incomeCntPerVertex.compute(singleEdge.dest, (notUsedKey, prevCnt) -> prevCnt == null ? 1 : prevCnt + 1);
        }

        Queue<String> queue = new ArrayDeque<>(sourceVertexes(allVertices, incomeCntPerVertex));

        List<String> res = new ArrayList<>();

        while (!queue.isEmpty()) {
            String curVer = queue.poll();

            res.add(curVer);

            for (String neighbourVer : graph.getAdjacent(curVer)) {

                @SuppressWarnings("ConstantConditions")
                int newIncomeDegree = incomeCntPerVertex.compute(neighbourVer, (notUsedKey, cnt) -> cnt - 1);

                if (newIncomeDegree == 0) {
                    queue.add(neighbourVer);
                }
            }
        }

        if (res.size() == allVertices.size()) {
            return res;
        }

        throw new IllegalStateException("Graph has cycle, so can't be sorted in topological order");
    }

    private static List<String> sourceVertexes(List<String> allVertices, Map<String, Integer> incomeCntPerVertex) {
        return allVertices.stream().
                filter(vertex -> !incomeCntPerVertex.containsKey(vertex)).
                collect(Collectors.toList());
    }


}
