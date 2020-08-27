package com.max.erickson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {

        Map<String, Map<String, Integer>> graph = new HashMap<>();
        Map<String, Integer> aList = new LinkedHashMap<>();
        aList.put("B", 5);
        aList.put("D", 7);

        graph.put("A", aList);

        for (Map.Entry<String, Integer> entry : aList.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("main done... java version: " + System.getProperty("java.version"));
    }
}
