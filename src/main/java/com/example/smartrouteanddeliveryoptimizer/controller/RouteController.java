package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.AlgorithmResult;
import com.example.smartrouteanddeliveryoptimizer.dto.LocationRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.City;
import com.example.smartrouteanddeliveryoptimizer.entity.Road;
import com.example.smartrouteanddeliveryoptimizer.service.implementation.RouteServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteServiceImplementation routeServiceImplementation;

    public RouteController(RouteServiceImplementation routeServiceImplementation) {
        this.routeServiceImplementation = routeServiceImplementation;
    }

    @PostMapping("/find")
    public RouteResponse findRoute(
            @Valid  @RequestBody RouteRequest routeRequest
    ) {
        return routeServiceImplementation.findShortestRoute(routeRequest);
    }

    @GetMapping("/algorithms")
    public List<String> getAlgorithms() {
        return routeServiceImplementation.getAvailableAlgorithms();
    }

    @GetMapping("/roads")
    public List<Road> getRoads() {
        return routeServiceImplementation.getAllRoads();
    }

    @PostMapping("/compare")
    public List<AlgorithmResult> compareAlgorithms(
            @Valid @RequestBody RouteRequest routeRequest
    ) {
        return routeServiceImplementation.compareAlgorithms(routeRequest);
    }

    @PostMapping("/nearest-city")
    public City findNearestCity(
            @RequestBody LocationRequest locationRequest
    ) {
        return routeServiceImplementation.findNearestCity(
                locationRequest.getLatitude(),
                locationRequest.getLongitude()
        );
    }

    @PatchMapping("/roads/{roadId}/block")
    public Road blockRoad(
            @PathVariable long roadId
    ) {
        return routeServiceImplementation.blockRoad(roadId);
    }

    @PatchMapping("/roads/")
    public Road unblockRoad(
            @PathVariable long roadId
    ) {
        return routeServiceImplementation.unblockRoad(roadId);
    }
}
