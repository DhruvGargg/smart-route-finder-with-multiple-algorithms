package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderSelectionResponse;
import com.example.smartrouteanddeliveryoptimizer.service.OrderSelectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class OrderSelectionController {

    private final OrderSelectionService orderSelectionService;

    public OrderSelectionController(OrderSelectionService orderSelectionService) {
        this.orderSelectionService = orderSelectionService;
    }

    @GetMapping("/{tripId}/orders/select")
    public OrderSelectionResponse selectOrders(
            @PathVariable Long tripId
    ) {
        return orderSelectionService
                .selectOrdersForTrip(
                        tripId
                );
    }
}
