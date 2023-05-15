package com.example.JobPortal.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@Table(name = "loginToken")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {
    @Id
    @SequenceGenerator(name = "token_sequence",sequenceName = "token_sequence",allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "token_sequence")
    int id;
    private String loginToken;
    private String email;

}
