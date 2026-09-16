package com.example.smartrouteanddeliveryoptimizer.dto;

import com.example.smartrouteanddeliveryoptimizer.enums.OrderPriority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelectedOrderResponse {

    private Long orderId;

    private Long destinationCityId;

    private String destinationCityName;

    private Double packageWeight;

    private OrderPriority orderPriority;

    private Double priorityScore;
}
