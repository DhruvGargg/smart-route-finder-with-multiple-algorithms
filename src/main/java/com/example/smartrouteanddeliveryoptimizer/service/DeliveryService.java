package com.example.smartrouteanddeliveryoptimizer.service;

import com.example.smartrouteanddeliveryoptimizer.dto.AssignedDeliveryResponse;
import com.example.smartrouteanddeliveryoptimizer.dto.DeliveryRequest;
import com.example.smartrouteanddeliveryoptimizer.entity.Delivery;
import com.example.smartrouteanddeliveryoptimizer.entity.Vehicle;
import com.example.smartrouteanddeliveryoptimizer.entity.Warehouse;

import java.util.List;
import java.util.PriorityQueue;

public interface DeliveryService {

    Delivery createDelivery(
            DeliveryRequest deliveryRequest
    );

    List<Delivery> getAllDeliveries();

    PriorityQueue<Delivery> buildDeliveryQueue();

    Delivery getNextDelivery();

    List<Delivery> getDeliveryProcessingOrder();

    AssignedDeliveryResponse assignNextDelivery();

    Warehouse findBestWareHouse(
            String destination
    );

    Vehicle findSuitableVehicle(
            double weight
    );
}
