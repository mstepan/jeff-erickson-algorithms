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

    private BipartiteGraph() {
        throw new AssertionError("Can;t instantiate utility only class");
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
