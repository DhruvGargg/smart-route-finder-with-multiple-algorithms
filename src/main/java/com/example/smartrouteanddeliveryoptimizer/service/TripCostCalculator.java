package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.entity.Truck;
import org.springframework.stereotype.Component;

@Component
public class TripCostCalculator {

    private static final double FUEL_PRICE = 100.0;

    public double calculateFuelCost(
            double distance,
            Truck truck
    ) {
        if(truck.getFuelEfficiency() <= 0) {
            throw new IllegalArgumentException("Fuel efficiency must be greater than 0.");
        }
        double fuelConsumed =
                distance / truck.getFuelEfficiency();

        return fuelConsumed * FUEL_PRICE;
    }
}
