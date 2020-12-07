package com.max.erickson;

import com.max.erickson.graph.Graph;
import com.max.erickson.graph.TopologicalSorting;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        Graph graph = Graph.newOrdered();

        graph.addEdge("A", "D");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("C", "E");
        graph.addEdge("D", "E");

        List<String> topologicalOrder = TopologicalSorting.topologicalOrder(graph);

        System.out.println(topologicalOrder);

        System.out.println("main done... java version: " + System.getProperty("java.version"));
    }
}
