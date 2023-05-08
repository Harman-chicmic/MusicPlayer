package com.example.dummySbrigit.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="roles")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int rId;
    @Column(name="roleName",nullable = false,unique = true)
    String roleName;

//    @OneToMany(mappedBy="roleId",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,orphanRemoval = true)
//    List<UserRole> userRole;
}
