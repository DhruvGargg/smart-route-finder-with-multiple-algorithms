package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderPriorityResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.DeliveryOrder;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.enums.OrderPriority;
import com.example.smartrouteanddeliveryoptimizer.repository.DeliveryOrderRepository;
import com.example.smartrouteanddeliveryoptimizer.service.DeliveryOrderService;
import com.example.smartrouteanddeliveryoptimizer.service.OrderPriorityService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OrderPriorityServiceImplementation implements OrderPriorityService {

    private final DeliveryOrderRepository deliveryOrderRepository;

    public OrderPriorityServiceImplementation(DeliveryOrderRepository deliveryOrderRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
    }
    @Override
    public OrderPriorityResponse calculateOrderPriority(Long orderId) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository
                .findById(orderId)
                .orElseThrow(() ->
                    new RuntimeException("Order id " + orderId + " not found")
                );
        return calculate(deliveryOrder);
    }

    @Override
    public List<OrderPriorityResponse> calculatePendingPriorities() {
        return deliveryOrderRepository
                .findByStatusOrderByDeliveryDeadlineAsc(
                        DeliveryStatus.PENDING
                )
                .stream()
                .map(this::calculate)
                .toList();
    }

    private OrderPriorityResponse calculate(DeliveryOrder deliveryOrder) {
        LocalDateTime now = LocalDateTime.now();
        long hoursRemaining = Duration
                .between(
                        now,
                        deliveryOrder.getDeliveryDeadline()
                )
                .toHours();
        OrderPriority orderPriority;
        if(hoursRemaining <= 24) {
            orderPriority = OrderPriority.CRITICAL;
        }
        else if(hoursRemaining <= 72) {
            orderPriority = OrderPriority.HIGH;
        }
        else {
            orderPriority = OrderPriority.NORMAL;
        }
        double priorityScore;
        if(hoursRemaining <= 0) {
            priorityScore = 100.0;
        }
        else {
            long totalDeliveryHours = 7 * 24L;
            priorityScore = 100.0 - (double) hoursRemaining /  totalDeliveryHours * 100.0;
            priorityScore = Math.max(
                    0.0,
                    Math.min(
                            100.0,
                            priorityScore
                    )
            );
        }
        return new OrderPriorityResponse(
                deliveryOrder.getId(),
                orderPriority,
                priorityScore,
                hoursRemaining,
                deliveryOrder.getDeliveryDeadline()
        );
    }
}
