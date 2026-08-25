package com.example.smartrouteanddeliveryoptimizer.algorithm;

import com.example.smartrouteanddeliveryoptimizer.entity.City;
import com.example.smartrouteanddeliveryoptimizer.repository.CityRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AStarAlgorithm implements Algorithm {

    private final CityRepository cityRepository;

    public AStarAlgorithm(
            CityRepository cityRepository
    ) {
        this.cityRepository = cityRepository;
    }

    @Override
    public RouteResult findShortestPath(
            String source,
            String destination,
            Map<String, List<Edge>> graph
    ) {

        Map<String, Coordinates> coordinates = new HashMap<>();
        for(City city : cityRepository.findAll()) {
            coordinates.put(
                    city.getName(),
                    new Coordinates(city.getLatitude(), city.getLongitude())
            );
        }

        long startTime = System.nanoTime();

        Map<String, Integer> gScore = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        PriorityQueue<AStarNode> priorityQueue =
                new PriorityQueue<>(
                        Comparator.comparingDouble(AStarNode::getFScore)
                );

        for(String node : graph.keySet()) {
            gScore.put(node, Integer.MAX_VALUE);
        }

        gScore.put(source, 0);

        double initialHeuristic =
                heuristic(source, destination, coordinates);

        priorityQueue.add(
                new AStarNode(
                        source,
                        0,
                        initialHeuristic
                )
        );

        while(!priorityQueue.isEmpty()) {

            AStarNode current = priorityQueue.remove();

            String currentNode = current.getNode();

            if(currentNode.equals(destination)) {
                break;
            }

            for(Edge edge : graph.get(currentNode)) {
                String neighbour = edge.getDestination();

                int newGScore =
                        gScore.get(currentNode) + edge.getDistance();

                if(newGScore < gScore.get(neighbour)) {

                    gScore.put(neighbour, newGScore);
                    parent.put(neighbour, currentNode);

                    double hScore = heuristic(
                            neighbour,
                            destination,
                            coordinates);

                    priorityQueue.add(
                            new AStarNode(
                                    neighbour,
                                    newGScore,
                                    newGScore + hScore
                            )
                    );
                }
            }
        }
        long executionTime = (System.nanoTime() - startTime) / 1_000_000;

        if(!gScore.containsKey(destination)
                || gScore.get(destination) == Integer.MAX_VALUE) {
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
                gScore.get(destination),
                executionTime
        );
    }
    private double heuristic(
            String current,
            String destination,
            Map<String, Coordinates> coordinates
    ) {
        Coordinates currentCityCoordinate = coordinates.get(current);

        Coordinates destinationCityCoordinate = coordinates.get(destination);

        if(currentCityCoordinate == null || destinationCityCoordinate == null) {
            return 0;
        }

        return calculateDistance(
                currentCityCoordinate,
                destinationCityCoordinate
        );
    }
    private double calculateDistance(
            Coordinates current,
            Coordinates destination
    ) {
        double latitudeDifference =
                current.getLatitude() - destination.getLatitude();

        double longitudeDifference =
                current.getLongitude() - destination.getLongitude();

        return Math.sqrt(latitudeDifference * latitudeDifference + longitudeDifference * longitudeDifference);
    }
    private List<String> buildPath(
            String source,
            String destination,
            Map<String, String> parent
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

    @Override
    public String getName() {
        return "A*";
    }
}
