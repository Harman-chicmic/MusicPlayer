package com.example.dummySbrigit.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pickup_dropoff")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PickupDropoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "pickup_location")
    private String pickupLocation;
    @Column(name = "dropoff_location")
    private String dropoffLocation;
    @Column(name = "distance")
    private Double distance;



}

