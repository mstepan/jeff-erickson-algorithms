package com.max.erickson.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Graph representation as an adjacency list.
 * Undirected.
 */
public class Graph {

    private enum Type {
        UNORDERED,
        ORDERED
    }

    private final Type type;

    private final Map<String, List<Edge>> vertexes = new HashMap<>();

    public Graph() {
        this.type = Type.UNORDERED;
    }

    private Graph(Type type) {
        this.type = type;
    }


    public static Graph newOrdered() {
        return new Graph(Type.ORDERED);
    }

    /**
     * Get list of all vertexes.
     */
    public List<String> vertexes() {
        return new ArrayList<>(vertexes.keySet());
    }

    public List<Edge> edges() {
        List<Edge> allEdges = new ArrayList<>();

        for (Map.Entry<String, List<Edge>> entry : vertexes.entrySet()) {
            allEdges.addAll(entry.getValue());
        }

        return allEdges;
    }

    /**
     * Returns the total vertexes count in all connected components.
     */
    public int vertexesCount() {
        return vertexes.size();
    }


    /**
     * Returns list of adjacent vertexes.
     */
    public List<String> getAdjacent(String src) {

        if (!vertexes.containsKey(src)) {
            return Collections.emptyList();
        }

        return vertexes.get(src).stream()
                .map(Edge::getDest)
                .collect(Collectors.toList());
    }

    /**
     * Returns any random vertex from graph.
     */
    public Optional<String> randomVertex() {

        if (vertexes.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(vertexes.keySet().iterator().next());
    }

    /**
     * Add src - dest and dest - src edges to an undirected graph.
     */
    public boolean addEdge(String src, String dest) {
        addVertex(src);
        addVertex(dest);

        if (tryAddEdge(src, dest)) {
            if (type == Type.UNORDERED) {
                return tryAddEdge(dest, src);
            }
        }

        return false;
    }

    private boolean tryAddEdge(String src, String dest) {
        if (vertexes.containsKey(src)) {

            // do not allow duplicate edges (not a multigraph)
            if (hasEdge(dest, vertexes.get(src))) {
                return false;
            }

            vertexes.get(src).add(Edge.of(dest));
            return true;
        }

        List<Edge> edges = new ArrayList<>();
        edges.add(Edge.of(dest));

        vertexes.put(src, edges);
        return true;
    }

    private static boolean hasEdge(String destEdge, List<Edge> edges) {
        for (Edge singleEdge : edges) {
            if (destEdge.equals(singleEdge.dest)) {
                return true;
            }
        }
        return false;
    }

    public void addVertex(String singleVertex) {
        if (!vertexes.containsKey(singleVertex)) {
            vertexes.put(singleVertex, new ArrayList<>());
        }
    }

    public boolean isOrdered() {
        return type == Type.ORDERED;
    }

    static final class Edge {
        final String dest;
        final int weight;

        static Edge of(String destVertex) {
            return new Edge(destVertex, 1);
        }

        String getDest() {
            return dest;
        }

        Edge(String dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return dest + ", weight: " + weight;
        }
    }

}
