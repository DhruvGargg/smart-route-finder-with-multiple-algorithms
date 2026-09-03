package com.example.smartrouteanddeliveryoptimizer.algorithm;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BFSAlgorithm implements Algorithm {

    @Override
    public RouteResult findShortestPath(
            String source,
            String destination,
            Map<String, List<Edge>> graph
    ) {
        long startTime = System.nanoTime();

        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        HashMap<String, String> parent = new HashMap<>();

        queue.add(source);
        visited.add(source);

        while(!queue.isEmpty()) {

            String current = queue.remove();

            if(current.equals(destination)) {
                break;
            }
            for(Edge edge : graph.getOrDefault(current, Collections.emptyList())) {

                String neighbour =edge.getDestination();

                if(!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    parent.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }
        long executionTime = (System.nanoTime() - startTime) / 1_000_000;

        if(!visited.contains(destination)) {
            return new RouteResult(
                    Collections.emptyList(),
                    -1,
                    executionTime
            );
        }
        List<String> path = buildPath(source, destination, parent);
        int totalDistance = calculateDistance(path, graph);

        return new RouteResult(
                path,
                totalDistance,
                executionTime
        );
    }

    @Override
    public String getName() {
        return "BFS";
    }

    private List<String> buildPath(
            String source,
            String destination,
            HashMap<String, String> parent
    ) {
        LinkedList<String> path = new LinkedList<>();

        String current = destination;

        while(current != null) {
            path.addFirst(current);
            if(current.equals(source)) {
                break;
            }
            current = parent.get(current);
        }
        return path;
    }
    private int calculateDistance(
            List<String> path,
            Map<String, List<Edge>> graph
    ) {
        int totalDistance = 0;
        for(int i = 0; i < path.size() - 1; i++) {
            String current = path.get(i);
            String next = path.get(i+1);
            for(Edge edge : graph.get(current)) {
                if(edge.getDestination().equals(next)) {
                    totalDistance += edge.getDistance();
                    break;
                }
            }
        }
        return totalDistance;
    }
}
