package com.example.dummySbrigit.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "uuid")
@NoArgsConstructor
public class Uuid {

    private String uuid;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
    private String email;

}
