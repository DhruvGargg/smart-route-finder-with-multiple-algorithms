package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.CreateDeliveryOrderRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.DeliveryOrderResponse;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryOrderService {

    DeliveryOrderResponse createOrder(
            CreateDeliveryOrderRequest request
    );

    DeliveryOrderResponse getOrder(Long orderId);

    List<DeliveryOrderResponse> getAllOrders();

    List<DeliveryOrderResponse> getOrdersByStatus(
            DeliveryStatus status
    );

    DeliveryOrderResponse cancelOrder(Long orderId);
}
