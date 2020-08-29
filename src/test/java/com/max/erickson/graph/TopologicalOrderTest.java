package com.max.erickson.graph;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class TopologicalOrderTest {

    @Test
    public void findTopologicalOrderNormalCase() {
        Graph g = Graph.newOrdered();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "F");
        g.addEdge("C", "D");
        g.addEdge("C", "E");
        g.addEdge("E", "F");
        g.addEdge("D", "G");
        g.addEdge("F", "G");

        Optional<List<String>> topologicalOrder = TopologicalOrder.findTopologicalOrder(g);

        assertThat(topologicalOrder).isNotEmpty();
        assertThat(topologicalOrder.get()).containsExactly("A", "B", "C", "D", "E", "F", "G");
    }

    @Test
    public void noTopologicalOrderIfCycleInGraph() {
        Graph g = Graph.newOrdered();
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "A");

        assertThat(TopologicalOrder.findTopologicalOrder(g)).isEmpty();
    }
}
