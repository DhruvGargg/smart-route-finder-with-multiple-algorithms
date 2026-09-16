package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.OrderPriorityResponse;
import com.example.smartrouteanddeliveryoptimizer.entity.DeliveryOrder;
import com.example.smartrouteanddeliveryoptimizer.entity.Trip;
import com.example.smartrouteanddeliveryoptimizer.enums.OrderPriority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class OrderPriorityCalculator {

    public OrderPriorityResponse calculateOrderPriority(DeliveryOrder deliveryOrder) {
        LocalDateTime now  = LocalDateTime.now();
        long hoursRemaining = Duration.between(
                now,
                deliveryOrder.getDeliveryDeadline()
        ).toHours();
        OrderPriority orderPriority;
        if (hoursRemaining <= 24) {
            orderPriority = OrderPriority.CRITICAL;
        }
        else if (hoursRemaining <= 72) {
            orderPriority = OrderPriority.HIGH;
        }
        else {
            orderPriority = OrderPriority.NORMAL;
        }
        double score;
        if(hoursRemaining <= 0) {
            score = 100.0;
        }
        else {
            score = 100.0 -
                    ((double) hoursRemaining / (7 * 24)) * 100.0;
            score = Math.max(0.0, score);
        }
        return new OrderPriorityResponse(
                deliveryOrder.getId(),
                orderPriority,
                score,
                hoursRemaining,
                deliveryOrder.getDeliveryDeadline()
        );
    }
}
