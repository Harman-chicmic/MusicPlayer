package com.example.dummySbrigit.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence",sequenceName = "user_sequence",allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "user_sequence")
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
    private String country;
    private boolean deleted;
    private boolean status;

    int otp;
    boolean otpVerified;
    String imgUrl;

//    @Where(clause = "deleted = false And roleId.roleName != 'ADMIN'")
    @OneToMany(mappedBy="userId",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<UserRole> userRoles;
    @OneToMany(mappedBy="userId",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<UserRole> k;
}
