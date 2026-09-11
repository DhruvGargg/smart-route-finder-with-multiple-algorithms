package com.example.smartrouteanddeliveryoptimizer.repository;

import com.example.smartrouteanddeliveryoptimizer.entity.Truck;
import com.example.smartrouteanddeliveryoptimizer.enums.TruckStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {

    Optional<Truck> findByRegistrationNumber(String registrationNumber);

    List<Truck> findByStatus(TruckStatus status);
}
