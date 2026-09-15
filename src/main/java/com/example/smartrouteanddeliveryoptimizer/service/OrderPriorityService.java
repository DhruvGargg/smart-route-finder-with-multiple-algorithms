package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderPriorityResponse;

import java.util.List;

public interface OrderPriorityService {

    OrderPriorityResponse calculateOrderPriority(Long orderId);

    List<OrderPriorityResponse> calculatePendingPriorities();
}
