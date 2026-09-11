package com.example.smartrouteanddeliveryoptimizer.controller;

import com.example.smartrouteanddeliveryoptimizer.dto.CreateTruckRequest;
import com.example.smartrouteanddeliveryoptimizer.dto.TruckResponse;
import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import com.example.smartrouteanddeliveryoptimizer.service.TruckService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {

    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping("/{truckId}")
    public TruckResponse getTruck(
            @PathVariable Long truckId
    ) {
        return truckService.getTruck(truckId);
    }

    @PostMapping
    public TruckResponse createTruck(
            @Valid @RequestBody CreateTruckRequest request
    ) {
        return truckService.createTruck(request);
    }

    @GetMapping
    public List<TruckResponse> getAllTrucks() {
        return truckService.getAllTrucks();
    }

    @GetMapping("/status/{status}")
    public List<TruckResponse> getTrucksByStatus(
            @PathVariable TruckStatus status
            ) {
        return truckService.getTrucksByStatus(status);
    }
}
