package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.OptimizedRouteResponse;
import com.example.smartrouteanddeliveryoptimizer.service.RouteOptimizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class RouteOptimizationController {

    private final RouteOptimizationService routeOptimizationService;

    public  RouteOptimizationController(RouteOptimizationService routeOptimizationService) {
        this.routeOptimizationService = routeOptimizationService;
    }

    @GetMapping("/{tripId}/optimize-route")
    public OptimizedRouteResponse optimizedRoute(
            @PathVariable Long tripId
    ) {
        return routeOptimizationService
                .optimizeTrip(tripId);
    }
}
