package com.example.dummySbrigit.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "ride")
public class RideDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ride_id")
    int id;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;
    String pickUp;
    String dropOff;
    int distance;
    int amount;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
