package com.max.erickson.graph;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BipartiteGraphTest {

    @Test
    public void bipartiteNormalCase(){
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("A", "D");

        assertThat(BipartiteGraph.isBipartite(graph)).isTrue();
    }
    @Test
    public void emptyGraphShouldBeBipartite() {
        assertThat(BipartiteGraph.isBipartite(new Graph())).isTrue();
    }

    @Test
    public void bipartiteTwoVertexesOneEdge(){
        Graph graph = new Graph();
        graph.addEdge("A", "B");

        assertThat(BipartiteGraph.isBipartite(graph)).isTrue();
    }

    @Test
    public void notBipartiteCheck(){
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        assertThat(BipartiteGraph.isBipartite(graph)).isFalse();
    }

    @Test
    public void notBipartiteForDisconnectedGraph(){
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("A", "D");

        graph.addEdge("E", "F");

        assertThat(BipartiteGraph.isBipartite(graph)).isFalse();
    }

    @Test
    public void notBipartiteIfTwoVertexesButNoEdge(){
        Graph graph = new Graph();
        graph.addVertex("A");
        graph.addVertex("B");

        assertThat(BipartiteGraph.isBipartite(graph)).isFalse();
    }
}
