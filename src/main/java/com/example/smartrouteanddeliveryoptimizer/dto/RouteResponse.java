package com.example.smartrouteanddeliveryoptimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.RouteMatcher;

import java.util.List;

@Getter
@AllArgsConstructor
public class RouteResponse {

    private String algorithm;

    private List<String> path;

    private int distance;

    private long executionTime;
}
