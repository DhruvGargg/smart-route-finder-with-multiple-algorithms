package com.example.smartrouteanddeliveryoptimizer.enums;

import lombok.Getter;

@Getter
public enum VehicleType {

    BIKE(10),
    VAN(100),
    TRUCK(1000);

    private final int capacity;

    VehicleType(int capacity) {
        this.capacity = capacity;
    }
}
