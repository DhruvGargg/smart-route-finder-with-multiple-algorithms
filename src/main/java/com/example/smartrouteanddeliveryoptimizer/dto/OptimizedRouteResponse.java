package com.example.smartrouteanddeliveryoptimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OptimizedRouteResponse {

    private Long tripId;

    private Long truckId;

    private String startCity;

    private String endCity;

    private List<String> route;

    private List<OptimizedStopResponse> stops;

    private Double totalDistance;

    private Double totalArrivalTime;

    private Double estimatedFuelCost;

    private Double totalCost;
}
