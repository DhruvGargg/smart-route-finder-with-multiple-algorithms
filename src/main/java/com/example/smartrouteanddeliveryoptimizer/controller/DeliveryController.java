package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.AssignedDeliveryResponse;
import com.example.smartrouteanddeliveryoptimizer.dto.DeliveryRequest;
import com.example.smartrouteanddeliveryoptimizer.entity.Delivery;
import com.example.smartrouteanddeliveryoptimizer.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(
            DeliveryService deliveryService
    ) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public Delivery createDelivery(
            @Valid @RequestBody DeliveryRequest request
    ) {
        return deliveryService.createDelivery(request);
    }

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/assign-next")
    public AssignedDeliveryResponse assignNextDelivery() {
        return deliveryService.assignNextDelivery();
    }

    @GetMapping("/priority-order")
    public List<Delivery> getDeliveryProcessingOrder() {
        return deliveryService.getDeliveryProcessingOrder();
    }
}
