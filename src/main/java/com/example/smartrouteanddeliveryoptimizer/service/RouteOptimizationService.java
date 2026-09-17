package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.OptimizedRouteResponse;
import com.example.smartrouteanddeliveryoptimizer.dto.OptimizedStopResponse;

public interface RouteOptimizationService {

    OptimizedRouteResponse optimizeTrip(Long tripId);
}
