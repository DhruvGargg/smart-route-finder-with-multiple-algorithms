package com.example.smartrouteanddeliveryoptimizer.dto;

import com.example.smartrouteanddeliveryoptimizer.enums.OrderPriority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderPriorityResponse {

    private Long id;

    private OrderPriority priority;

    private Double priorityScore;

    private Long hoursRemaining;

    private LocalDateTime deliveryDate;
}
