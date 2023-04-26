package com.example.dummySbrigit.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@Table(name = "drivers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drivers {
    @Id
    @SequenceGenerator(name = "driver_sequence",sequenceName = "driver_sequence",allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "driver_sequence")
    private int  id;
    @Size(min =3)
    @NotNull
    private String firstName;
    private String lastName;
    @Email
    @NotNull
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String phone;
    private String password;
    private String city;
    private boolean deleted;
    private boolean status;
    int otp;
    boolean otpVerified;


}