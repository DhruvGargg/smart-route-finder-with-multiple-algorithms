package com.example.smartrouteanddeliveryoptimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlgorithmResult {

    private String algorithm;

    private List<String> path;

    private Integer distance;

    private Long executionTime;

}
