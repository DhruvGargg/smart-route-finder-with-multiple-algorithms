package com.example.smartrouteanddeliveryoptimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptimizedStopResponse {

    private Integer sequenceNumber;

    private Long cityId;

    private String cityName;

    private Double arrivalTimeHours;

    private Double cumulativeDistance;
}
