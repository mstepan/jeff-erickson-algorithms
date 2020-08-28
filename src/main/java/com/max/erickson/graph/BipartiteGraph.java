package com.max.erickson.graph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Exercise: 3c. Check if graph is bipartite.
 */
public class BipartiteGraph {

    public static void main(String[] args) {

        Graph graph1 = new Graph();
        graph1.addEdge("A", "B");
        graph1.addEdge("A", "C");
        graph1.addEdge("B", "C");
        System.out.println(isBipartite(graph1));

        Graph graph2 = new Graph();
        graph2.addEdge("A", "B");
        graph2.addEdge("B", "C");
        graph2.addEdge("C", "D");
        graph2.addEdge("A", "D");
        System.out.println(isBipartite(graph2));

        System.out.println("Bipartite check completed...");
    }

    /**
     * Check if graph is bipartite (can be colored with just 2 colors).
     * time: O(V + 2E)
     * space: O(V)
     */
    public static boolean isBipartite(Graph graph) {

        Objects.requireNonNull(graph, "null 'graph detected'");

        Optional<String> maybeFirst = graph.randomVertex();

        // empty graph is always bipartite
        if (maybeFirst.isEmpty()) {
            return true;
        }

        String first = maybeFirst.get();

        Map<String, Boolean> marked = new HashMap<>();
        marked.put(first, Boolean.TRUE);

        Deque<String> stack = new ArrayDeque<>();
        stack.push(first);

        while (!stack.isEmpty()) {

            String cur = stack.pop();
            boolean color = marked.get(cur);
            boolean otherColor = !color;

            for (String adj : graph.getAdjacent(cur)) {
                if (marked.containsKey(adj)) {
                    if (marked.get(adj) != otherColor) {
                        return false;
                    }
                }
                else {
                    marked.put(adj, otherColor);
                    stack.push(adj);
                }
            }
        }

        // check if graph is disconnected, if yes - return false (can't be bipartite)
        return marked.size() == graph.vertexesCount();
    }


}
