package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderPriorityResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.DeliveryOrder;
import com.example.smartrouteanddeliveryoptimizer.service.OrderPriorityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders/priority")
public class OrderPriorityController {

    private final OrderPriorityService orderPriorityService;

    public OrderPriorityController(OrderPriorityService orderPriorityService) {
        this.orderPriorityService = orderPriorityService;
    }

    @GetMapping("/{orderId}")
    public OrderPriorityResponse calculateOrderPriority(
            @PathVariable Long orderId
    ) {
        return orderPriorityService.calculateOrderPriority(orderId);
    }

    @GetMapping("/pending")
    public List<OrderPriorityResponse> calculatePendingPriorities() {
        return orderPriorityService.calculatePendingPriorities();
    }
}
