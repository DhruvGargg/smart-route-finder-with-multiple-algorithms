package com.example.smartrouteanddeliveryoptimizer.enums;

import lombok.Getter;

@Getter
public enum DeliveryType {

    PRIORITY(12),
    EXPRESS(24),
    STANDARD(72);

    private final int deliveryHours;

    DeliveryType(int deliveryHours) {
        this.deliveryHours = deliveryHours;
    }

}
