package com.example.smartrouteanddeliveryoptimizer.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Algorithm {

    RouteResult findShortestPath(
            String source,
            String destination,
            Map<String, List<Edge>> graph
    );

    String getName();
}
