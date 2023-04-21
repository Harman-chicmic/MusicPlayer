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
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @SequenceGenerator(name = "user_sequence",sequenceName = "user_sequence",allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "user_sequence")
    private int  id;
    @Size(min =3)
    @NotNull
    private String firstName;
    private String lastName;
    private String password;
    @Email
    @NotNull
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String phone;
    private String country;
    private String imgUrl;
}

