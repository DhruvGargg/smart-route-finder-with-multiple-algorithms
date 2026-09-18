package com.example.smartrouteanddeliveryoptimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RouteEvaluation {

    private Double totalDistance;

    private Double totalTimeTaken;

    private Double totalCost;

    private Double normalizedTime;

    private Double normalizedCost;

    private Double finalScore   ;
}
