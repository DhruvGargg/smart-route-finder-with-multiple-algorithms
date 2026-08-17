package com.example.smartrouteanddeliveryoptimizer.repository;

import com.example.smartrouteanddeliveryoptimizer.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByNameIgnoreCase(String name);
}
