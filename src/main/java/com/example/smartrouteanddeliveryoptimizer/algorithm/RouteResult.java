package com.example.smartrouteanddeliveryoptimizer.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RouteResult {

    private List<String> path;

    private Integer distance;

    private Long executionTime;
}
