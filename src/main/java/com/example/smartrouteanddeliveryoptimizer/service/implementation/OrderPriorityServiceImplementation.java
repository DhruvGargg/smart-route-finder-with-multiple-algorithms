package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderPriorityResponse;
import com.example.smartrouteanddeliveryoptimizer.service.OrderPriorityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPriorityServiceImplementation implements OrderPriorityService {


    @Override
    public OrderPriorityResponse calculateOrderPriority(Long orderId) {
        return null;
    }

    @Override
    public List<OrderPriorityResponse> calculatePendingPriorities() {
        return List.of();
    }
}
