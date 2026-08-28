package com.example.smartrouteanddeliveryoptimizer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AssignedDeliveryResponse {

    private Long deliveryId;
    private String destination;
    private LocalDateTime deadline;
    private String algorithm;
    private List<String> path;
    private Integer distance;
    private Long executionTime;
}
