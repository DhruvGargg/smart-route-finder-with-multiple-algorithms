package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.algorithm.Algorithm;
import com.example.smartrouteanddeliveryoptimizer.algorithm.Edge;
import com.example.smartrouteanddeliveryoptimizer.algorithm.RouteResult;
import com.example.smartrouteanddeliveryoptimizer.dto.AlgorithmResult;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.City;
import com.example.smartrouteanddeliveryoptimizer.entity.Road;
import com.example.smartrouteanddeliveryoptimizer.enums.RoadStatus;
import com.example.smartrouteanddeliveryoptimizer.repository.CityRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.RoadRepository;
import com.example.smartrouteanddeliveryoptimizer.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteServiceImplementation implements RouteService {

    private final RoadRepository roadRepository;
    private final List<Algorithm> algorithms;
    private final CityRepository cityRepository;

    public RouteServiceImplementation(
            RoadRepository roadRepository,
            List<Algorithm> algorithms,
            CityRepository cityRepository
    ) {
        this.roadRepository = roadRepository;
        this.algorithms = algorithms;
        this.cityRepository = cityRepository;
    }

    public List<AlgorithmResult> compareAlgorithms(
            RouteRequest routeRequest
    ) {

        validateCities(routeRequest);

        List<Road> roads = roadRepository.findAll();

        Map<String, List<Edge>> graph = buildGraph(roads);

        List<AlgorithmResult> results = new ArrayList<>();

        for(Algorithm algorithm : algorithms) {

            RouteResult routeResult =
                    algorithm.findShortestPath(
                            routeRequest.getSource(),
                            routeRequest.getDestination(),
                            graph
                    );
            results.add(
                    new AlgorithmResult(
                            algorithm.getName(),
                            routeResult.getPath(),
                            routeResult.getDistance(),
                            routeResult.getExecutionTime()
                    )
            );
        }
        return results;
    }

    public RouteResponse findShortestRoute(
            String source,
            String destination,
            String algorithm
    ) {
        return findShortestRoute(new RouteRequest(source, destination, algorithm));
    }

    public RouteResponse findShortestRoute(RouteRequest routeRequest) {

        validateCities(routeRequest);

        List<Road> roadList = roadRepository.findByStatus(RoadStatus.ACTIVE);

        Map<String, List<Edge>> graph = buildGraph(roadList);

        Algorithm selectedAlgorithm = algorithms
                .stream()
                .filter(algorithm ->
                        algorithm.getName()
                                .equalsIgnoreCase(
                                        routeRequest.getAlgorithm()
                                )
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unsupported Algorithm : "
                                + routeRequest.getAlgorithm()
                        )
                );

        RouteResult routeResult = selectedAlgorithm.findShortestPath(
                routeRequest.getSource(),
                routeRequest.getDestination(),
                graph
        );
        return new RouteResponse(
                selectedAlgorithm.getName(),
                routeResult.getPath(),
                routeResult.getDistance(),
                routeResult.getExecutionTime()
        );
    }
    private Map<String, List<Edge>> buildGraph(
            List<Road> roads
    ) {
        Map<String, List<Edge>> graph = new HashMap<>();

        for(Road road : roads) {
            if(road.getStatus() == RoadStatus.BLOCKED) {
                continue;
            }
            if(!graph.containsKey(road.getSource())) {
                graph.put(road.getSource(), new ArrayList<>());
            }
            if(!graph.containsKey(road.getDestination())) {
                graph.put(road.getDestination(), new ArrayList<>());
            }
            graph.get(road.getSource())
                    .add(
                            new Edge(
                                    road.getDestination(),
                                    road.getDistance()
                            )
                    );
            graph.get(road.getDestination())
                    .add(
                            new Edge(
                                    road.getSource(),
                                    road.getDistance()
                            )
                    );
        }
        return graph;
    }

    public List<String> getAvailableAlgorithms() {
        return algorithms
                .stream()
                .map(Algorithm::getName)
                .toList();
    }

    public List<Road> getAllRoads() {
        return roadRepository
                .findAll();
    }

    public City findNearestCity(
            double latitude,
            double longitude
    ) {
        List<City> cities = cityRepository.findAll();

        City nearestCity = null;
        double minimumDistance = Double.MAX_VALUE;

        for(City city : cities) {

            double distance = calculateDistance(
                    latitude,
                    longitude,
                    city.getLatitude(),
                    city.getLongitude()
            );
            if(distance < minimumDistance) {
                minimumDistance = distance;
                nearestCity = city;
            }
        }
        return  nearestCity;
    }

    //Haversine Calculation to find actual geographic distance in kilometers
    private double calculateDistance(
            double latitude1,
            double longitude1,
            double latitude2,
            double longitude2
    ) {
        final double EARTH_RADIUS = 6371;

        double latitudeDifference = Math.toRadians(latitude2 - latitude1);

        double longitudeDifference = Math.toRadians(longitude2 - longitude1);

        double a =
                Math.sin(latitudeDifference / 2)
                * Math.sin(latitudeDifference / 2)
                +
                Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(latitude2))
                * Math.sin(longitudeDifference / 2)
                * Math.sin(longitudeDifference / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a)
                );

        return EARTH_RADIUS * c;
    }
    private void validateCities(
            RouteRequest routeRequest
    ) {
        cityRepository
                .findByNameIgnoreCase(
                        routeRequest.getSource()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Source city is not supported: "
                                        + routeRequest.getSource()
                        )
                );

        cityRepository
                .findByNameIgnoreCase(
                        routeRequest.getDestination()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Destination city is not supported: "
                                        + routeRequest.getDestination()
                        )
                );
    }

    public Road unblockRoad(Long roadId) {
        Road road = roadRepository.findById(roadId)
                .orElseThrow(() -> new RuntimeException("Invalid Road Id"));
        road.setStatus(RoadStatus.ACTIVE);
        return roadRepository.save(road);
    }

    public Road blockRoad(Long roadId) {
        Road road = roadRepository.findById(roadId)
                .orElseThrow(() -> new RuntimeException("Invalid Road Id"));
        road.setStatus(RoadStatus.BLOCKED);
        return roadRepository.save(road);
    }
}
