package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.CreateTruckRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.TruckResponse;
import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;

import java.util.List;

public interface TruckService {

    TruckResponse createTruck(CreateTruckRequest request);

    TruckResponse getTruck(Long truckId);

    List<TruckResponse> getAllTrucks();

    List<TruckResponse> getTrucksByStatus(TruckStatus status);
}
