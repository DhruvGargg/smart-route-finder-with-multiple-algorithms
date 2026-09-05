package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.AlgorithmResult;
import com.example.smartrouteanddeliveryoptimizer.dto.LocationRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.RouteResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.City;
import com.example.smartrouteanddeliveryoptimizer.entity.Road;
import com.example.smartrouteanddeliveryoptimizer.service.RouteService;
import com.example.smartrouteanddeliveryoptimizer.service.implementation.RouteServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/find")
    public RouteResponse findRoute(
            @Valid  @RequestBody RouteRequest routeRequest
    ) {
        return routeService.findShortestRoute(routeRequest);
    }

    @GetMapping("/algorithms")
    public List<String> getAlgorithms() {
        return routeService.getAvailableAlgorithms();
    }

    @GetMapping("/roads")
    public List<Road> getRoads() {
        return routeService.getAllRoads();
    }

    @PostMapping("/compare")
    public List<AlgorithmResult> compareAlgorithms(
            @Valid @RequestBody RouteRequest routeRequest
    ) {
        return routeService.compareAlgorithms(routeRequest);
    }

    @PostMapping("/nearest-city")
    public City findNearestCity(
            @RequestBody LocationRequest locationRequest
    ) {
        return routeService.findNearestCity(
                locationRequest.getLatitude(),
                locationRequest.getLongitude()
        );
    }

    @PatchMapping("/roads/{roadId}/block")
    public Road blockRoad(
            @PathVariable long roadId
    ) {
        return routeService.blockRoad(roadId);
    }

    @PatchMapping("/roads/{roadId}/unblock")
    public Road unblockRoad(
            @PathVariable long roadId
    ) {
        return routeService.unblockRoad(roadId);
    }
}
