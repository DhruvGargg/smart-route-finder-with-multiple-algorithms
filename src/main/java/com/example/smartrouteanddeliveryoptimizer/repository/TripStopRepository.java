package com.example.smartrouteanddeliveryoptimizer.repository;

import com.example.smartrouteanddeliveryoptimizer.entity.TripStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripStopRepository extends JpaRepository<TripStop, Long> {

    List<TripStop> findByTripIdOrderBySequenceNumberAsc(
            Long tripId
    );

    Optional<TripStop> findByTripIdAndCityId(
            Long tripId,
            Long cityId
    );
}
