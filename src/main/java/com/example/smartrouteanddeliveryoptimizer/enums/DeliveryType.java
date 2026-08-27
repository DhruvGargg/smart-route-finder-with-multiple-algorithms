package com.example.smartrouteanddeliveryoptimizer.enums;

import lombok.Getter;

@Getter
public enum DeliveryPriority {

    PRIORITY(12),
    EXPRESS(24),
    STANDARD(72);

    private final int deliveryHours;

    DeliveryPriority(int deliveryHours) {
        this.deliveryHours = deliveryHours;
    }

}
