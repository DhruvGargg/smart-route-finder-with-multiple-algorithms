package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.entity.Vehicle;
import com.example.smartrouteanddeliveryoptimizer.repository.VehicleRepository;
import com.example.smartrouteanddeliveryoptimizer.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class VehicleServiceImplementation implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImplementation(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle findSuitableVehicle(double weight) {
        List<Vehicle> availableVehicles =
                vehicleRepository.findByAvailableTrue();
        return availableVehicles
                .stream()
                .filter(vehicle ->
                        vehicle.getType()
                                .getCapacity() >= weight)
                .min(
                        Comparator.comparingInt(
                                vehicle ->
                                        vehicle.getType()
                                                .getCapacity()
                        )
                )
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No suitable vehicles available for "
                                + weight + "kg"
                        )
                );
    }
}
