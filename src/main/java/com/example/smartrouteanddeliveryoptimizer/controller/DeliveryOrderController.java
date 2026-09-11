package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.DeliveryOrderResponse;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.service.DeliveryOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class DeliveryOrderController {

    private final DeliveryOrderService deliveryOrderService;

    public DeliveryOrderController(DeliveryOrderService deliveryOrderService) {
        this.deliveryOrderService = deliveryOrderService;
    }

    @GetMapping("/{orderId}")
    public DeliveryOrderResponse getOrderById(
            @PathVariable Long orderId
    ) {
        return deliveryOrderService.getOrder(orderId);
    }

    @GetMapping
    public List<DeliveryOrderResponse> getAllOrders()
    {
        return deliveryOrderService.getAllOrders();
    }

    @GetMapping("/status/{status}")
    public List<DeliveryOrderResponse> getOrdersByStatus(
            @PathVariable DeliveryStatus status
    ) {
        return deliveryOrderService.getOrdersByStatus(status);
    }

    @PatchMapping("/{orderId}/cancel")
    public DeliveryOrderResponse cancelOrder(
            @PathVariable Long orderId
    ) {
        return deliveryOrderService.cancelOrder(orderId);
    }
}
