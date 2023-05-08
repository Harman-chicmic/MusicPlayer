package com.example.dummySbrigit.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RideDetails {
    @Id
    int id;
    String pickUp;
    String dropOff;

}
