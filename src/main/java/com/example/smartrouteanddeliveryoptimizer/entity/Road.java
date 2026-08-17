package com.example.smartrouteanddeliveryoptimizer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roads")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    String source;

    @Column(nullable = false)
    String destination;

    @Column(nullable = false)
    int distance;

    @Min(0)
    private Integer travelTime;

}
