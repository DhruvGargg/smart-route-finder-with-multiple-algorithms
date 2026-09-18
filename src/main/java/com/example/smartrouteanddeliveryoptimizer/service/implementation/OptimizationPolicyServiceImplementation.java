package com.example.smartrouteanddeliveryoptimizer.service.implementation;

import com.example.smartrouteanddeliveryoptimizer.dto.OptimizationPolicy;
import com.example.smartrouteanddeliveryoptimizer.enums.DeliveryStatus;
import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import com.example.smartrouteanddeliveryoptimizer.repository.DeliveryOrderRepository;
import com.example.smartrouteanddeliveryoptimizer.repository.TruckRepository;
import com.example.smartrouteanddeliveryoptimizer.service.OptimizationPolicyService;
import org.springframework.stereotype.Service;

@Service
public class OptimizationPolicyServiceImplementation implements OptimizationPolicyService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final TruckRepository truckRepository;

    public OptimizationPolicyServiceImplementation(
            DeliveryOrderRepository deliveryOrderRepository,
            TruckRepository truckRepository
    ) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.truckRepository = truckRepository;
    }

    @Override
    public OptimizationPolicy determinePolicy() {
        int pendingOrders = deliveryOrderRepository
                .findByStatus(DeliveryStatus.PENDING)
                .size();

        int availableTrucks = truckRepository
                .findByStatus(TruckStatus.AVAILABLE)
                .size();

        if(pendingOrders <= 5) {
            return new OptimizationPolicy(
                    0.50,
                    0.50,
                    "Normal workload: Balanced time and cost"
            );
        }

        if(availableTrucks == 0 || pendingOrders >= availableTrucks * 5) {
            return new OptimizationPolicy(
                    0.90,
                    0.10,
                    "Critical workload: Delivery time prioritized"
            );
        }

        return new OptimizationPolicy(
                0.75,
                0.25,
                "High workload: Delivery time prioritized"
        );
    }
}
