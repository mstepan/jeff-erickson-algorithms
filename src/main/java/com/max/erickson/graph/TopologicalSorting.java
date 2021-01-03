package com.max.erickson.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

public final class TopologicalSorting {

    private TopologicalSorting() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    public static List<String> topologicalOrderUsingDFS(Graph graph) {
        checkGraphOrdered(graph);

        Set<String> visited = new HashSet<>();
        Set<String> marked = new HashSet<>();

        List<String> dfsOrder = new ArrayList<>(graph.vertexesCount());

        for (String ver : graph.vertexes()) {
            if (!visited.contains(ver)) {
                dfs(ver, graph, dfsOrder, marked, visited);
            }
        }

        Collections.reverse(dfsOrder);
        return dfsOrder;
    }

    private static void dfs(String vertex, Graph graph, List<String> dfsOrder, Set<String> marked, Set<String> visited) {

        assert !visited.contains(vertex) : "vertex found ins 'visited' set, but should not: " + vertex;

        marked.add(vertex);

        Deque<String> stack = new ArrayDeque<>();
        stack.push(vertex);

        MAIN:
        while (!stack.isEmpty()) {

            String cur = stack.peek();

            for (String adjVer : graph.getAdjacent(cur)) {
                if (marked.contains(adjVer)) {
                    // cycle detected
                    throw new IllegalStateException("Graph has cycle, so can't be sorted in topological order");
                }

                if (!visited.contains(adjVer)) {
                    // new vertex for DFS found
                    marked.add(adjVer);
                    stack.push(adjVer);
                    continue MAIN;
                }
            }

            stack.pop();

            boolean wasRemoved = marked.remove(cur);
            assert wasRemoved : "vertex wasn't removed from 'marked' set: " + cur;

            boolean wasNew = visited.add(cur);
            assert wasNew : "vertex was already in visited state: " + cur;

            dfsOrder.add(cur);
        }
    }

    /**
     * Classic topological sorting for directed acyclic graph (DAG).
     * If graph has a cycle this method will throw IllegalStateException.
     * If graph is unordered or graph reference is NULL, this method will throw IllegalArgumentException.
     * <p>
     * time: O(V+E)
     * space: O(V)
     * V - vertexes count in a graph
     * E - edges count in a graph
     */
    public static List<String> topologicalOrder(Graph graph) {
        checkGraphOrdered(graph);

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

    private static void checkGraphOrdered(Graph graph) {
        checkArgument(graph != null, "NULL 'graph' reference detected");
        checkArgument(graph.isOrdered(), "Can't execute topological sorting for UNDIRECTED graph.");
    }
}
