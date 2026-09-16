package com.example.smartrouteanddeliveryoptimizer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip_stops",
        uniqueConstraints = {
            @UniqueConstraint(
                    columnNames = {"trip_id", "sequence_number"}
            )
        }
)
public class TripStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "trip_id",
            nullable = false
    )
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "city_id",
            nullable = false
    )
    private City city;

    @Column(
            nullable = false
    )
    private Integer sequenceNumber;

    @Column(nullable = false)
    private Boolean deliveryStop = true;
}
