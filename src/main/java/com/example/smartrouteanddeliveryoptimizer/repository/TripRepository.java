package com.example.smartrouteanddeliveryoptimizer.repository;

import com.example.smartrouteanddeliveryoptimizer.entity.Trip;
import com.example.smartrouteanddeliveryoptimizer.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByStatus(TripStatus status);

    List<Trip> findByTruckId(Long truckId);
}
