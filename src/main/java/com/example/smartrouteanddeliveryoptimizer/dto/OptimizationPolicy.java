package com.example.smartrouteanddeliveryoptimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptimizationPolicy {

    private Double timeWeight;

    private Double costWeight;

    private String reason;
}
