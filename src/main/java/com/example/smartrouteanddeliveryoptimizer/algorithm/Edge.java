package com.example.smartrouteanddeliveryoptimizer.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Edge {

    private String destination;
    private Integer distance;
}
