package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.entity.Vehicle;

public interface VehicleService {

    Vehicle findSuitableVehicle(double weight);

}
