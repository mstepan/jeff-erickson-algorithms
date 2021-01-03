package com.max.erickson.graph;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class TopologicalSortingTest {

    @Test
    public void topologicalSortUsingDFS() {
        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "D");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("C", "E");
        graph.addEdge("D", "E");

        List<String> topologicalOrder = TopologicalSorting.topologicalOrderUsingDFS(graph);

        assertThat(topologicalOrder).containsExactly("B", "A", "C", "D", "E");
    }

    @Test
    public void disconnectedGraphHasTopologicalOrder() {
        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");

        graph.addEdge("D", "E");

        List<String> topologicalOrder = TopologicalSorting.topologicalOrder(graph);

        assertThat(topologicalOrder).hasSameElementsAs(List.of("A", "B", "C", "D", "E"));
    }

    @Test
    public void normalDagCase1() {
        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "D");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("C", "E");
        graph.addEdge("D", "E");

        List<String> topologicalOrder = TopologicalSorting.topologicalOrder(graph);

        assertThat(topologicalOrder).containsExactly("A", "B", "C", "D", "E");
    }

    @Test
    public void normalDagCase2() {
        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");

        List<String> topologicalOrder = TopologicalSorting.topologicalOrder(graph);

        assertThat(topologicalOrder).hasSameElementsAs(List.of("A", "B", "C"));
    }

    @Test
    public void graphWithOneEdge() {
        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "B");

        List<String> topologicalOrder = TopologicalSorting.topologicalOrder(graph);

        assertThat(topologicalOrder).hasSameElementsAs(List.of("A", "B"));
    }

    @Test
    public void graphWithCycleCase1ThrowsException() {
        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "B");

        assertThatThrownBy(() -> TopologicalSorting.topologicalOrder(graph)).
                isInstanceOf(IllegalStateException.class).
                hasMessage("Graph has cycle, so can't be sorted in topological order");

    }

    @Test
    public void graphWithCycleCase2ThrowsException() {
        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        assertThatThrownBy(() -> TopologicalSorting.topologicalOrder(graph)).
                isInstanceOf(IllegalStateException.class).
                hasMessage("Graph has cycle, so can't be sorted in topological order");

    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void throwsExceptionForNullGraph() {
        assertThatThrownBy(() -> TopologicalSorting.topologicalOrder(null)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("NULL 'graph' reference detected");

    }

    @Test
    public void throwsExceptionForUnorderedGraph() {
        Graph graph = new Graph();
        graph.addEdge("A", "B");

        assertThatThrownBy(() -> TopologicalSorting.topologicalOrder(graph)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("Can't execute topological sorting for UNDIRECTED graph.");

    }
}
