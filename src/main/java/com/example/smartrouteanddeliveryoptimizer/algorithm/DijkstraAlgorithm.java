package com.example.smartrouteanddeliveryoptimizer.algorithm;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DijkstraAlgorithm implements Algorithm {

    @Override
    public RouteResult findShortestPath(
            String source,
            String destination,
            Map<String, List<Edge>> graph
    ) {
        long startTime = System.nanoTime();

        HashMap<String, Integer> distance = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(Node::getDistance)
        );

        for(String node : graph.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
        }

        distance.put(source, 0);

        priorityQueue.add(new Node(source, 0));

        while(!priorityQueue.isEmpty()) {
            Node current = priorityQueue.remove();

            String currentNode = current.getNode();
            int currentDistance = current.getDistance();

            if(currentDistance > distance.get(currentNode)) {
                continue;
            }
            if(currentNode.equals(destination)) {
                break;
            }

            for(Edge edge : graph.getOrDefault(currentNode, Collections.emptyList())) {

                String neighbour = edge.getDestination();

                int newDistance = currentDistance + edge.getDistance();

                if(newDistance < distance.get(neighbour)) {
                    distance.put(neighbour, newDistance);
                    parent.put(neighbour, currentNode);
                    priorityQueue.add(new Node(neighbour, newDistance));
                }
            }
        }

        long executionTime =  (System.nanoTime() - startTime)/ 1_000_000;
        if(!distance.containsKey(destination)
                || distance.get(destination) == Integer.MAX_VALUE
        ) {
            return new RouteResult(
                    Collections.emptyList(),
                    -1,
                    executionTime
            );
        }

        List<String> path = buildPath(
                source,
                destination,
                parent
        );
        return new RouteResult(
                path,
                distance.get(destination),
                executionTime
        );
    }

    @Override
    public String getName() {
        return "Dijkstra";
    }

    private List<String> buildPath(
            String source,
            String destination,
            HashMap<String, String> parent
    )
    {
        LinkedList<String> path = new LinkedList<>();

        String current = destination;

        while(current != null)
        {
            path.addFirst(current);
            if(current.equals(source)) {
                break;
            }
            current = parent.get(current);
        }
        return path;
    }
}
