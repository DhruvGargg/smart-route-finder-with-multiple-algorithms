package com.example.smartrouteanddeliveryoptimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderSelectionResponse {

    private Long tripId;

    private Long truckId;

    private Double truckCapacity;

    private Double selectedWeight;

    private Double remainingCapacity;

    private Double totalPriorityScore;

    private List<SelectedOrderResponse> selectedOrders;

    private String selectedStrategy;
}
