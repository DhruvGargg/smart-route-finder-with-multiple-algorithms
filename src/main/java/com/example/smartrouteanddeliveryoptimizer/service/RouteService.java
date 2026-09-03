package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.algorithm.Edge;
import com.example.smartrouteanddeliveryoptimizer.dto.AlgorithmResult;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.City;
import com.example.smartrouteanddeliveryoptimizer.entity.Road;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

public interface RouteService {

    List<AlgorithmResult> compareAlgorithms(
            RouteRequest routeRequest
    );

    RouteResponse findShortestRoute(
            RouteRequest routeRequest
    );

    List<String> getAvailableAlgorithms();

    List<Road> getAllRoads();

    City findNearestCity(
            double latitude,
            double longitude
    );

    Road unblockRoad(
            Long roadId
    );

    Road blockRoad(
            Long roadId
    );
}
